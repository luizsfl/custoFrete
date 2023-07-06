package com.example.custofrete.presentation.calculo

import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoBinding
import com.example.custofrete.domain.model.Distance
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.GoogleMapDTO
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.adapter.RotaAdapter
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import java.text.DecimalFormat

class CalculoFragment : Fragment() {

    private var _binding: FragmentCalculoBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<CalculoFragmentArgs>()
    private lateinit var entrega: Entrega
    private var menorRotaAdapter: MutableList<Rota> = mutableListOf()
    lateinit var googleMap: GoogleMap
    private var posicaoMelhorRota = 0
    private var kmMelhorRota = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        entrega = args.value.entrega

        entrega.listaRotas?.let { setHomeListAdapter(it) }

        distanciaRota(entrega.listaRotas,1)

        calculoMenorRota()

        binding.lnRotaInformada.setOnClickListener {
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.white))

            entrega.listaRotas?.let { setHomeListAdapter(it) }
            distanciaRota(entrega.listaRotas,1)

        }

        binding.lnRotaCalculada.setOnClickListener {
            binding.lnRotaCalculada.setBackgroundColor(getResources().getColor(R.color.botonSelected))
            binding.lnRotaInformada.setBackgroundColor(getResources().getColor(R.color.white))
            setHomeListAdapter(menorRotaAdapter)

            binding.tvValorTotalKm.text = "Essa rota percorre $kmMelhorRota km"

        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root

    }

    private fun calculoMenorRota() {
        if (entrega.listaRotas?.size!! > 0) {
            val points = mutableListOf<Point>()
            entrega.listaRotas?.forEachIndexed { index, rota ->
                points.add(Point(rota.lat, rota.lng, index))
            }

            val menorRota = nearestNeighborAlgorithm(points)
            menorRotaAdapter = mutableListOf<Rota>()

            menorRota.forEach {
                val rota = entrega.listaRotas!![it.posicao].copy()
                rota.valorDistance = Distance()

                menorRotaAdapter.add(rota)
            }


            for (i in 0..menorRotaAdapter.size) {
                if (i > 1) {
                    val tste = menorRotaAdapter
                    val location1 = LatLng(menorRotaAdapter.get(i - 2).lat,menorRotaAdapter.get(i - 2).lng)
                    val location2 = LatLng(menorRotaAdapter.get(i - 1).lat,menorRotaAdapter.get(i - 1).lng)

                    var start = Location("Start Point");
                    start.setLatitude(location1.latitude);
                    start.setLongitude(location1.longitude);

                    var finish = Location("Finish Point");

                    finish.setLatitude(location2.latitude);
                    finish.setLongitude(location2.longitude);

                    // Log.d("GoogleMap", "before URL")
                    var URL = getDirectionURL(location1, location2)
                    // Log.d("GoogleMap", "URL : $URL")
                    posicaoMelhorRota = i
                    GetDirection(URL).execute()
                }
            }
        }
    }

    private fun distanciaRota(listRota: List<Rota>?,tipoCalculo:Int) {
        var valorMetroSequencia = 0.0
        listRota?.forEach {
            valorMetroSequencia += it.valorDistance.value
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        val random = valorMetroSequencia / 1000

        val df = DecimalFormat("#.#")
        val roundoff = df.format(random)

        if(tipoCalculo == 1)
        {
            binding.tvValorTotalKm.text = "Essa rota percorre $roundoff km"

        }else if (tipoCalculo == 2){
            kmMelhorRota = roundoff
        }


    }

    private fun setHomeListAdapter(listRota: List<Rota>) {
        val rotaAdapter = RotaAdapter(listRota)
        rotaAdapter.onItemClick = {
//            val intent = Intent(requireContext(), HomeDetailActivity::class.java)
//                .apply {
//                    putExtra("idCaixa", it.idCaixa)
//                }
//            startActivity(intent)
        }

        binding.recyclerview.adapter = rotaAdapter
    }


    fun nearestNeighborAlgorithm(points: List<Point>): List<Point> {
        val visitedPoints = mutableListOf<Point>()
        var currentPoint = points.first()
        visitedPoints.add(currentPoint)
        while (visitedPoints.size < points.size) {
            val nextPoint = points.filter { !visitedPoints.contains(it) }
                .minByOrNull { it.distanceTo(currentPoint) }
            nextPoint?.let {
                visitedPoints.add(it)
                currentPoint = it
            }
        }
        return visitedPoints
    }

    data class Point(val latitude: Double, val longitude: Double, val posicao: Int) {
        fun distanceTo(other: Point): Double {
            val earthRadius = 6371 // km
            val dLat = Math.toRadians(other.latitude - latitude)
            val dLon = Math.toRadians(other.longitude - longitude)
            val lat1 = Math.toRadians(latitude)
            val lat2 = Math.toRadians(other.latitude)
            val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)
            val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
            return earthRadius * c
        }
    }

    private inner class GetDirection(val url: String) :
        AsyncTask<Void, Void, List<List<LatLng>>>() {
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap", " data : $data")
            val result = ArrayList<List<LatLng>>()
            try {
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path = ArrayList<LatLng>()
                menorRotaAdapter.get(posicaoMelhorRota - 1).valorDistance =
                    respObj.routes.get(0).legs.get(0).distance

                result.add(path)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            if(posicaoMelhorRota == menorRotaAdapter.size ){
                distanciaRota(menorRotaAdapter,2)
            }
        }

    }

    fun getDirectionURL(origin: LatLng, dest: LatLng): String {
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&key=AIzaSyA2TWLwHJhNZtJ867ipr_5XhQZMGKm49Os"
    }

}