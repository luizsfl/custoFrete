package com.example.custofrete.presentation.rotas

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentRotasBinding
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.adapter.PlaceAutoSuggestAdapter
import com.example.custofrete.presentation.adapter.RotaAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class RotasFragment : Fragment() {

    private var _binding: FragmentRotasBinding? = null
    private val binding get() = _binding!!
    private val listaRota: MutableList<Rota> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRotasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

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
            val action = RotasFragmentDirections.actionRotasFragmentToCalculoFragment()
            findNavController().navigate(action)
        }

        selectRota()

        return root

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
                        latLng = latLng
                    )

                    binding.llMapa.getMapAsync { google ->
                        google.moveCamera(CameraUpdateFactory.newLatLngZoom(rota.latLng, 13f))
                        addMarkers(google)
                    }

                    listaRota.add(rota)
                    val listaRotaInvest = listaRota.map { it.copy() }
                    setHomeListAdapter(listaRotaInvest.reversed())
                    binding.btEnderecoEntrega.setText("")
                    binding.tvParadaSelecionada.text = listaRota.size.toString()

                } else {
                    Log.d("Adddress", "Address Not Found")
                }
            } else {
                Log.d("Lat Lng", "Lat Lng Not Found")
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
        val rotaAdapter = RotaAdapter(listHome)
        rotaAdapter.onItemClick = {
//            val intent = Intent(requireContext(), HomeDetailActivity::class.java)
//                .apply {
//                    putExtra("idCaixa", it.idCaixa)
//                }
//            startActivity(intent)
        }

        binding.recyclerview.adapter = rotaAdapter
        showLoading(false)
    }

    private fun showLoading(isLoading: Boolean) {
       // binding.progressBar.isVisible = isLoading
    }

    private fun addMarkers(googleMap: GoogleMap){
        val markes = listaRota.forEach {place ->
            googleMap.addMarker(
                MarkerOptions().apply {
                    title(place.title)
                    snippet(place.address)
                    position(place.latLng)
                }
            )
        }
    }
}
