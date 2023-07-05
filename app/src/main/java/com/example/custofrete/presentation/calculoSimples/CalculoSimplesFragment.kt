package com.example.custofrete.presentation.calculoSimples

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoSimplesBinding
import com.example.custofrete.domain.model.EntregaSimples


class CalculoSimplesFragment : Fragment() {

    private var _binding: FragmentCalculoSimplesBinding? = null
    private val binding get() = _binding!!
    private var tipoCalculo = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoSimplesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.tiInKmPercorrido.setHint("Informe o total de km")
        binding.tiInValorKmInformado.setHint("Informe o valor cobrado por 1 Km")
        binding.tiOutraDespesa.setHint("Informe o valor de despesas extras")

        val items = listOf("Multiplicação", "Porcentagem")

        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_spinner, items)
        (binding.tiOpcao.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.tiInKmPercorrido.doOnTextChanged { _, _, _, _ ->
            var total = totalSimples()

            binding.tvValorKm.text = "Valor calculado R$: $total"

        }

        binding.tiInValorKmInformado.doOnTextChanged {  _, _, _, _ ->
            var total = totalSimples()

            binding.tvValorKm.text = "Valor calculado R$: $total"

        }

        binding.tiOutraDespesa.doOnTextChanged {  _, _, _, _ ->

            var total = totalSimples()

            binding.tvValorKm.text = "Valor calculado R$: $total"

        }

        binding.tvTipoCalculo.setOnItemClickListener { parent, view, position, id ->

            tipoCalculo = position

            when(position){
                0 -> {
                    binding.tiValorTipo.setHint("Informe o valor para multiplicar")
                    binding.tiValorTipo.requestFocus()
                    binding.tiValorTipo.visibility = View.VISIBLE
                }
                1 ->{
                    binding.tiValorTipo.setHint("Informe o valor para jogar de porcentagem")
                    binding.tiValorTipo.requestFocus()
                    binding.tiValorTipo.visibility = View.VISIBLE
                }
            }

            if(binding.tiValorTipo.text.toString().isNotEmpty()){
                var total = totalSimples()
                binding.tvValorKm.text = "Valor calculado R$: $total"
            }

        }

        binding.tiValorTipo.doOnTextChanged {  _, _, _, _ ->

            var total = totalSimples()

            binding.tvValorKm.text = "Valor calculado R$: $total"
        }

        binding.btCalculo.setOnClickListener {

            val valorKmInformado = if (binding.tiInKmPercorrido.text.toString().isEmpty()) 0.0 else binding.tiInKmPercorrido.text.toString().toDouble()
            val valorCobrado = if (binding.tiInValorKmInformado.text.toString().isEmpty()) 0.0 else binding.tiInValorKmInformado.text.toString().toDouble()
            val valorDespesaExtra = if (binding.tiOutraDespesa.text.toString().isEmpty()) 0.0 else binding.tiOutraDespesa.text.toString().toDouble()
            val ValorCalculo =  if (binding.tiValorTipo.text.toString().isEmpty()) 0.0 else binding.tiValorTipo.text.toString().toDouble()

            val entregaSimples = EntregaSimples(valorKmInformado,valorCobrado,valorDespesaExtra,tipoCalculo,ValorCalculo)

            calculoTotalSimples(requireContext(),entregaSimples)

        }

        return root
    }

    private fun calculoTotalSimples(contextTela : Context,entregaSimples:EntregaSimples){

        val builder = AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.total_custo_calc_simples,null)

        val tvValorCalculado = view.findViewById<TextView>(R.id.tv_valor_cobrado)
        val tvDescriCalculoSimples = view.findViewById<TextView>(R.id.tv_tipo_calculo_simples)
        val tvDespesaCalculoSimples = view.findViewById<TextView>(R.id.tv_despesas_extras)

        tvValorCalculado.setText("Valor Calculado R$: ${entregaSimples.valorCalculado()}")
        tvDescriCalculoSimples.setText("${entregaSimples.descricaoCalculo()}")
        tvDespesaCalculoSimples.text = "Despesas extras = ${entregaSimples.valorDespExtra}"

        builder.setView(view)
            .setTitle("")

        builder.setPositiveButton("Ok") { dialog, which -> }

        builder.setNegativeButton("Cancelar", null)


        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()

    }

    private fun totalSimples():Double {

        val valorKmInformado = if (binding.tiInKmPercorrido.text.toString().isEmpty()) 0.0 else binding.tiInKmPercorrido.text.toString().toDouble()
        val valorCobrado = if (binding.tiInValorKmInformado.text.toString().isEmpty()) 0.0 else binding.tiInValorKmInformado.text.toString().toDouble()
        val valorDespesaExtra = if (binding.tiOutraDespesa.text.toString().isEmpty()) 0.0 else binding.tiOutraDespesa.text.toString().toDouble()
        val ValorCalculo =  if (binding.tiValorTipo.text.toString().isEmpty()) 0.0 else binding.tiValorTipo.text.toString().toDouble()

        val valorKmCalc = (valorKmInformado * valorCobrado)
        var valorTotal = valorKmCalc + valorDespesaExtra

        if(tipoCalculo == 0){
             valorTotal += valorKmCalc * ValorCalculo - valorKmCalc
        }else if(tipoCalculo == 1){
            valorTotal +=  valorKmCalc * ValorCalculo/100
        }else{
            0.0
        }

        return valorTotal
    }
}