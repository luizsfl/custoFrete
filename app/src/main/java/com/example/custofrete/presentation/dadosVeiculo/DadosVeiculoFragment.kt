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
import com.example.custofrete.domain.model.CustoViagem
import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Entrega
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
            val placa = binding.tiPlaca.editText?.text.toString()
            val kmLitro = if (binding.tiKmDadosVeiculo.text.toString().isEmpty()) 0.0 else binding.tiKmDadosVeiculo.text.toString().toDouble()
            val kmEixo = if (binding.tiQtdEixo.text.toString().isEmpty()) 0.0 else binding.tiQtdEixo.text.toString().toDouble()
            val peso = if (binding.tiPesoVeiculo.text.toString().isEmpty()) 0.0 else binding.tiPesoVeiculo.text.toString().toDouble()

            val dadosVeiculo = DadosVeiculo(placa,kmLitro,kmEixo,peso,"idUsuario")
            if(validarDadosVeiculo(dadosVeiculo)){
               //addDadosVeiculo

            }

        }

        return root
    }

    fun validarDadosVeiculo(dadosVeiculo: DadosVeiculo):Boolean{

        var lret = true

        if (dadosVeiculo.placaVeiculo.isEmpty()){
            binding.tiPlaca.error = "Favor preencher a placa do veiculo"
            lret = false
        }else if (dadosVeiculo.qtdKmLitro<=0){
            binding.tiKmDadosVeiculo.error = "Favor preencher Km por quilometro"
            lret = false
        }

        return lret
    }


}