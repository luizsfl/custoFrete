package com.example.custofrete.presentation.entrega

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.databinding.FragmentEntregaBinding

class EntregaFragment : Fragment() {

    private var _binding: FragmentEntregaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEntregaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.show()

        binding.novaEntrega.setOnClickListener {
            val action =  EntregaFragmentDirections.actionEntregaFragmentToDadosVeiculoFragment()
            findNavController().navigate(action)
        }

        return root
    }
}