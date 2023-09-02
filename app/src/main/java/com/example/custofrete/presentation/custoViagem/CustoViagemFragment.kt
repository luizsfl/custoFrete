package com.example.custofrete.presentation.custoViagem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.custofrete.databinding.FragmentCustoViagemBinding
import com.example.custofrete.domain.model.CustoViagem
import com.example.custofrete.domain.model.Entrega

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

        setCustoViagem(args.value.entrega)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextCustoViagem.setOnClickListener {

            val kmLitro = if (binding.tiValorMediaLitro.text.toString().isEmpty()) 0.0 else binding.tiValorMediaLitro.text.toString().toDouble()
            val totalAlimentacao = if (binding.tiTotalGastpAlimentacao.text.toString().isEmpty()) 0.0 else binding.tiTotalGastpAlimentacao.text.toString().toDouble()
            val Totalhotel = if (binding.tiTotalGastoHotel.text.toString().isEmpty()) 0.0 else binding.tiTotalGastoHotel.text.toString().toDouble()
            val TotalExtras = if (binding.tiTotalGastoExtras.text.toString().isEmpty()) 0.0 else binding.tiTotalGastoExtras.text.toString().toDouble()

            val custoViagem = CustoViagem(totalAlimentacao,Totalhotel,kmLitro,TotalExtras)

            if(validarCurso(custoViagem)){

                val entrega = args.value.entrega
                entrega.custoViagem = custoViagem

                val action = CustoViagemFragmentDirections.actionCustoViagemFragmentToRotasFragment(entrega)
                findNavController().navigate(action)
            }
        }

        binding.ivVoltar.setOnClickListener {
            findNavController().navigateUp()
        }

        return root
    }

    fun validarCurso(custoViagem: CustoViagem) : Boolean{

        var lret = true

        if (custoViagem.valorGasolina<=0){
            binding.tiValorMediaLitro.error = "Favor preencher o valor medio da gasolina"
            lret = false
        }

        return lret
    }


fun setCustoViagem(entrega: Entrega){
    if(entrega.custoViagem!=null){
       if(entrega.custoViagem?.valorGasolina!! > 0){
            binding.tiValorMediaLitro.setText(entrega.custoViagem?.valorGasolina.toString())
        }
        if(entrega.custoViagem?.valorAlimentacao!! > 0){
            binding.tiTotalGastpAlimentacao.setText(entrega.custoViagem?.valorAlimentacao.toString())
        }
        if(entrega.custoViagem?.valorHotel!! > 0){
            binding.tiTotalGastoHotel.setText(entrega.custoViagem?.valorHotel.toString())
        }
        if(entrega.custoViagem?.gastosExtras!! > 0){
            binding.tiTotalGastoExtras.setText(entrega.custoViagem?.gastosExtras.toString())
        }
    }
}

}