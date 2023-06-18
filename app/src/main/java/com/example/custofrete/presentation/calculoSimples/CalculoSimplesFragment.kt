package com.example.custofrete.presentation.calculoSimples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoSimplesBinding


class CalculoSimplesFragment : Fragment() {

    private var _binding: FragmentCalculoSimplesBinding? = null
    private val binding get() = _binding!!
    private var valorCalculado = 0.0
    private var valorOutraDespesa = 0.0
    private var tipoCalculo = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoSimplesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val items = listOf("Multiplicação", "Porcentagem", "Valor")

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_spinner, items)
        (binding.tiOpcao.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.tiInKmPercorrido.doOnTextChanged { _, _, _, _ ->

            valorCalculado = calcValKm(binding.tiInKmPercorrido.text.toString(),binding.tiInValorKmInformado.text.toString())

            binding.tvValorKm.text = "Valor calculado R$: ${valorCalculado+valorOutraDespesa}"

        }

        binding.tiInValorKmInformado.doOnTextChanged {  _, _, _, _ ->

            valorCalculado = calcValKm(binding.tiInKmPercorrido.text.toString(),binding.tiInValorKmInformado.text.toString())

            binding.tvValorKm.text = "Valor calculado R$: ${valorCalculado+valorOutraDespesa}"

        }

        binding.tiOutraDespesa.doOnTextChanged {  _, _, _, _ ->

            if(binding.tiOutraDespesa.text.toString().isNotEmpty()){
                valorOutraDespesa = binding.tiOutraDespesa.text.toString().toDouble()

            }else{
                valorOutraDespesa = 0.0
            }

            binding.tvValorKm.text = "Valor calculado R$: ${valorCalculado+valorOutraDespesa}"

        }

        binding.tvTipoCalculo.setOnItemClickListener { parent, view, position, id ->

            tipoCalculo = position

            when(position){
                0 -> {
                    binding.tiValorTipo.setHint("Informe o valor para multiplicar")
                    binding.tiValorTipo.requestFocus()
                }
                1 ->{
                    binding.tiValorTipo.setHint("Informe o valor para jogar de porcentagem")
                    binding.tiValorTipo.requestFocus()
                }
                2->{
                    binding.tiValorTipo.setHint("Informe o valor que deseja cobrar")
                    binding.tiValorTipo.requestFocus()
                }
            }

        }

        binding.btCalculo.setOnClickListener {

            if (binding.tiValorTipo.text.toString().isNotEmpty()){
                if (tipoCalculo == 0) {
                    val valorTipoCalc = binding.tiValorTipo.text.toString().toDouble()
                    val total = (valorCalculado + valorOutraDespesa) * valorTipoCalc

                    binding.tvValorKm.text = "Valor calculado R$: $total"

                }else if (tipoCalculo == 1){
                    val valorTipoCalc = binding.tiValorTipo.text.toString().toDouble()
                    val total = (valorCalculado + valorOutraDespesa)+(((valorCalculado + valorOutraDespesa) * valorTipoCalc)/100)

                    binding.tvValorKm.text = "Valor calculado R$: $total"

                }else if(tipoCalculo == 2){
                    val total = binding.tiValorTipo.text.toString().toDouble()
                    //val total =  valorTipoCalc //((valorCalculado + valorOutraDespesa) * valorTipoCalc)/100

                    binding.tvValorKm.text = "Valor calculado R$: $total"
                }

            }else{
                //valor não informado
            }

        }


        return root
    }

    private fun calcValKm(val1:String,val2:String):Double {
       return  if (val1.isNotEmpty() && val2.isNotEmpty()
        ) {
             val1.toInt() * val2.toDouble()
        } else {
            0.0
        }
    }

}