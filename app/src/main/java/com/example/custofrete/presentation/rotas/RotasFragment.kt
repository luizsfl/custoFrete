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
import com.example.custofrete.databinding.FragmentRotasBinding
import com.example.custofrete.presentation.adapter.PlaceAutoSuggestAdapter
import com.google.android.gms.maps.model.LatLng


class RotasFragment : Fragment() {

    private var _binding: FragmentRotasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRotasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btEnderecoEntrega.setAdapter(
            PlaceAutoSuggestAdapter(
                binding.root.context,
                android.R.layout.simple_list_item_1
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
        binding.btEnderecoEntrega.setOnItemClickListener { parent, view, position, id ->
            Log.d("Address : ", binding.btEnderecoEntrega.getText().toString())
            val latLng: LatLng? =
                getLatLngFromAddress(binding.btEnderecoEntrega.getText().toString())
            if (latLng != null) {
                Log.d("Lat Lng : ", " " + latLng.latitude + " " + latLng.longitude)
                val address: Address? = getAddressFromLatLng(latLng)
                if (address != null) {
                    Log.d("tagtest_Address : ", "" + address.toString())
                    Log.d("tagtest_Address Line : ", "" + address.getAddressLine(0))
                    Log.d("tagtest_Phone : ", "" + address.getPhone())
                    Log.d("tagtest_Pin Code : ", "" + address.getPostalCode())
                    Log.d("tagtest_Feature : ", "" + address.getFeatureName())
                    Log.d("tagtest_More : ", "" + address.getLocality())
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


}