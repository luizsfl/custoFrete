package com.example.custofrete.presentation.custoViagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCustoViagemBinding
import com.example.custofrete.databinding.FragmentDadosVeiculoBinding
import com.example.custofrete.presentation.dadosVeiculo.DadosVeiculoFragmentDirections

class CustoViagemFragment : Fragment() {

    private var _binding: FragmentCustoViagemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCustoViagemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextCustoViagem.setOnClickListener {
            val action = CustoViagemFragmentDirections.actionCustoViagemFragmentToRotasFragment()
            findNavController().navigate(action)
        }

        return root
    }

}