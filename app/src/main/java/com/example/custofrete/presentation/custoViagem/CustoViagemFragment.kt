package com.example.custofrete.presentation.custoViagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCustoViagemBinding
import com.example.custofrete.databinding.FragmentDadosVeiculoBinding
import com.example.custofrete.domain.model.CustoViagem
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.presentation.dadosVeiculo.DadosVeiculoFragmentDirections

class CustoViagemFragment : Fragment() {

    private var _binding: FragmentCustoViagemBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<CustoViagemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCustoViagemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextCustoViagem.setOnClickListener {

            val custoViagem = CustoViagem(20f,30f,40f,2f)
            val dadosVeiculo = args.value.entrega.dadosVeiculo
            val entrega = Entrega(dadosVeiculo = dadosVeiculo, custoViagem = custoViagem)

            val action = CustoViagemFragmentDirections.actionCustoViagemFragmentToRotasFragment(entrega)
            findNavController().navigate(action)
        }

        return root
    }

}