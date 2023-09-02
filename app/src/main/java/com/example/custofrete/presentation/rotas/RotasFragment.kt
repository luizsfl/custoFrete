package com.example.custofrete.presentation.rotas

import android.graphics.*
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.BuildConfig.GOOGLE_MAPS_KEY
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentRotasBinding
import com.example.custofrete.domain.model.GoogleMapDTO
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.adapter.PlaceAutoSuggestAdapter
import com.example.custofrete.presentation.adapter.RotaAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request


class RotasFragment : Fragment() {

    private var _binding: FragmentRotasBinding? = null
    private val binding get() = _binding!!
    private var listaRota: MutableList<Rota> = mutableListOf()
    lateinit var googleMap: GoogleMap
    private val args = navArgs<RotasFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRotasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        if(args.value.entrega.listaRotas != null){
            listaRota = args.value.entrega.listaRotas!!.toMutableList()
        }

        binding.llMapa.onCreate(savedInstanceState)
        binding.llMapa.onResume()
        binding.llMapa.getMapAsync{google ->
            //mapa inicia no brasil
            google.moveCamera(CameraUpdateFactory.newLatLngZoom( LatLng(-13.3898276,-61.5396485), 3f))

        }

        binding.btEnderecoEntrega.setAdapter(
            PlaceAutoSuggestAdapter(
                binding.root.context,
                R.layout.autocomplete_list_rotas
            )
        )

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextRotas.setOnClickListener {
            if(listaRota.size>0){

                var entrega = args.value.entrega
                entrega.listaRotas = listaRota

                val action = RotasFragmentDirections.actionRotasFragmentToCalculoFragment(entrega)
                findNavController().navigate(action)
            }else{
                binding.btEnderecoEntrega.error = "Informe pelo menos uma rota"
            }

        }

        atualizaRotas()

        selectRota()

        return root

    }

    private fun atualizaRotas() {
        if (listaRota.size > 0) {
            val listaRotaInvest = listaRota.map { it.copy() }
            setHomeListAdapter(listaRotaInvest.reversed())

            listaRota.forEachIndexed{ indice, rota ->
                atualizaMapa(rota,indice+1)
            }
        }
    }

    private fun selectRota() {
        binding.btEnderecoEntrega.setOnItemClickListener { _, _, _, _ ->
            val latLng: LatLng? =
                getLatLngFromAddress(binding.btEnderecoEntrega.text.toString())
            if (latLng != null) {
                val address: Address? = getAddressFromLatLng(latLng)
                if (address != null) {

                    val rota = Rota(
                        title = binding.btEnderecoEntrega.text.toString(),
                        address = "" + address.toString(),
                        Address_line ="" + address.getAddressLine(0),
                        phone = "" +address.getPhone(),
                        pin_code = "" +address.getPostalCode(),
                        feature = "" + address.getFeatureName(),
                        more = "" + address.getLocality(),
                        lat = latLng.latitude,
                        lng = latLng.longitude
                    )

                    listaRota.add(rota)
                    val listaRotaInvest = listaRota.map { it.copy() }
                    setHomeListAdapter(listaRotaInvest.reversed())
                    binding.btEnderecoEntrega.setText("")

                    atualizaMapa(rota,listaRota.size)

                } else {
                    Log.d("Adddress", "Address Not Found")
                }
            } else {
                Log.d("Lat Lng", "Lat Lng Not Found")
            }
        }
    }

    private fun atualizaMapa(rota: Rota,posicaoMelhorRota:Int) {
        binding.llMapa.getMapAsync { google ->
            val posicaoCamera = LatLng(rota.lat,rota.lng)

            google.moveCamera(CameraUpdateFactory.newLatLngZoom(posicaoCamera, 13f))
            addMarkers(google)

            googleMap = google

            if (posicaoMelhorRota > 1) {
                //posicaoMelhorRota  = listaRota.size

                val location1 = LatLng(listaRota.get(posicaoMelhorRota - 2).lat,listaRota.get(posicaoMelhorRota - 2).lng)
                val location2 = LatLng(listaRota.get(posicaoMelhorRota - 1).lat,listaRota.get(posicaoMelhorRota - 1).lng)

                var start = Location("Start Point");
                start.setLatitude(location1.latitude);
                start.setLongitude(location1.longitude);

                var finish = Location("Finish Point");

                finish.setLatitude(location2.latitude);
                finish.setLongitude(location2.longitude);
                var distance = start.distanceTo(finish);

                val distanceKM = distance / 1000;

                // Log.d("GoogleMap", "before URL")
                var URL = getDirectionURL(location1, location2)
                // Log.d("GoogleMap", "URL : $URL")
                GetDirection(URL,posicaoMelhorRota).execute()
            }

        }
    }

    private fun getLatLngFromAddress(address: String): LatLng? {
        val geocoder = Geocoder(requireContext())
        val addressList: List<Address>?
        return try {
            addressList = geocoder.getFromLocationName(address, 1)
            if (addressList != null) {
                val singleaddress = addressList[0]

                return LatLng(singleaddress.latitude, singleaddress.longitude)

            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getAddressFromLatLng(latLng: LatLng): Address? {
        val geocoder = Geocoder(requireContext())
        val addresses: List<Address>?
        return try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5)
            addresses?.get(0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setHomeListAdapter(listHome: List<Rota>) {
        var rotaAdapter = RotaAdapter(listHome,true)

        rotaAdapter.onItemClickExcluir = {
            //necessário retirar o tamanho da lista porque a lista esta inversa.
            listaRota.removeAt((listaRota.size-1) - it)
            googleMap.clear()

            if(listaRota.size>0){
                atualizaRotas()
                selectRota()
            }else{
                rotaAdapter = RotaAdapter(arrayListOf())
                binding.recyclerview.adapter = rotaAdapter
            }

            args.value.entrega.listaRotas = listaRota

        }

        binding.recyclerview.adapter = rotaAdapter
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
       //binding.progressBar.isVisible = isLoading
    }

    private fun addMarkers(googleMap: GoogleMap){

      listaRota.forEachIndexed  { index, place ->
            googleMap.addMarker(
                MarkerOptions().apply {
                    title(place.title)
                    position(LatLng(place.lat,place.lng))
                    snippet(place.address)
                    icon(getBitmapDescriptor2(index+1))
                }
            )
        }
    }

    private fun getBitmapDescriptor2(id: Int): BitmapDescriptor {
        val drawable = resources.getDrawable(com.example.custofrete.R.drawable.baseline_circle_24)
        drawable.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        val paint = Paint()
        paint.setColor(Color.WHITE)
        paint.setTextSize(30F)
        paint.setTextAlign(Paint.Align.CENTER)
        canvas.drawText(id.toString(), bitmap.width / 2f, bitmap.height / 2f + 10, paint)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&key=${GOOGLE_MAPS_KEY}"
    }

    private inner class GetDirection(val url : String,val posicaoMelhorRota:Int) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }

                listaRota.get(posicaoMelhorRota - 1).valorDistance =
                    respObj.routes.get(0).legs.get(0).distance

                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            googleMap.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {

        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }

        return poly
    }
}
