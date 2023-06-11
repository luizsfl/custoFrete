package com.example.custofrete.presentation.dadosVeiculo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentDadosVeiculoBinding
import com.example.custofrete.databinding.FragmentHomeBinding
import com.example.custofrete.presentation.home.HomeFragmentDirections

class DadosVeiculoFragment : Fragment() {

    private var _binding: FragmentDadosVeiculoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDadosVeiculoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextDadosVeiculos.setOnClickListener {
            val action = DadosVeiculoFragmentDirections.actionDadosVeiculoFragmentToCustoViagemFragment()
            findNavController().navigate(action)
        }

        return root
    }


}