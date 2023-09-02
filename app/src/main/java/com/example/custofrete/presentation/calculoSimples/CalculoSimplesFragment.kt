package com.example.custofrete.presentation.calculoSimples

import android.content.Context
import android.os.Bundle
import android.text.BoringLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentCalculoSimplesBinding
import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.presentation.ViewStateEntregaSimples
import com.example.custofrete.presentation.listaEntregaSimples.ListaEntregaSimplesFragmentDirections
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import org.koin.androidx.viewmodel.ext.android.viewModel


class CalculoSimplesFragment : Fragment() {

    private var _binding: FragmentCalculoSimplesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalculoSimplesViewModel by viewModel()
    private var entrega : EntregaSimples? = null
    private val args = navArgs<CalculoSimplesFragmentArgs>()
    private var tipoTela : Int = 0
    private val items = listOf("Multiplicação", "Porcentagem")

    private var tipoCalculo = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoSimplesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        entrega = args.value.entregaSimples
        tipoTela = args.value.tipoTela

        entrega?.let {
            //Editar
            if(tipoTela==1){
                setDadosTela(it)
                binding.btCalculo.setText("Editar Cálculo")
            }else if(tipoTela == 2){
                //Visualizar
                setDadosTela(it,true)
                binding.btCalculo.setText("Voltar pra lista")
            }
        }


        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.ivVoltar.setOnClickListener {
            val action =  CalculoSimplesFragmentDirections.actionCalculoSimplesFragmentToListaEntregaSimplesFragment()
            findNavController().navigate(action)
        }

        binding.tiInKmPercorrido.setHint("Informe o total de km")
        binding.tiInValorKmInformado.setHint("Informe o valor cobrado por 1 Km")
        binding.tiOutraDespesa.setHint("Informe o valor de despesas extras")

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

            if(position >= 0 ) {

                tipoCalculo = position

                when (position) {
                    0 -> {
                        binding.tiValorTipo.setHint("Informe o valor para multiplicar")
                        binding.tiValorTipo.requestFocus()
                        binding.tiValorTipo.visibility = View.VISIBLE
                    }
                    1 -> {
                        binding.tiValorTipo.setHint("Informe o valor para jogar de porcentagem")
                        binding.tiValorTipo.requestFocus()
                        binding.tiValorTipo.visibility = View.VISIBLE
                    }
                }

                if (binding.tiValorTipo.text.toString().isNotEmpty()) {
                    var total = totalSimples()
                    binding.tvValorKm.text = "Valor calculado R$: $total"
                }
            }else{
                binding.tiValorTipo.setHint("Iteste err -1 ")

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
            //Adicionar
            if(tipoTela == 0){
                val entregaSimples = EntregaSimples( totalKm = valorKmInformado,valorInformado = valorCobrado,valorDespExtra = valorDespesaExtra,tipoCalc= tipoCalculo,valorTpCalc = ValorCalculo,valorEntregaCalculado = totalSimples())
                calculoTotalSimples(requireContext(),entregaSimples,tipoTela)
            }else if(tipoTela == 1){
                //update
                val entregaSimples = entrega!!
                entregaSimples.totalKm = valorKmInformado
                entregaSimples.valorInformado = valorCobrado
                entregaSimples.valorDespExtra = valorDespesaExtra
                entregaSimples.tipoCalc = tipoCalculo
                entregaSimples.valorTpCalc = ValorCalculo
                entregaSimples.valorEntregaCalculado = totalSimples()

                calculoTotalSimples(requireContext(),entregaSimples,tipoTela)

            }else if(tipoTela == 2){
                val action =  CalculoSimplesFragmentDirections.actionCalculoSimplesFragmentToListaEntregaSimplesFragment()
                findNavController().navigate(action)
            }

           // calculoTotalSimples(requireContext(),entregaSimples)

        }


        viewModel.viewStateEntregaSimples.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateEntregaSimples.Loading -> showLoading(viewState.loading)
                is ViewStateEntregaSimples.sucesso -> showSucess(viewState.entrega)
                is ViewStateEntregaSimples.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }


        return root
    }

    private fun showSucess(entregaSimples: EntregaSimples){
        val builder = android.app.AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Entrega simples criada com sucesso!!")
            setCancelable(false) //não fecha quando clicam fora do dialog
            setPositiveButton("OK") { dialog, which ->
                val action =  CalculoSimplesFragmentDirections.actionCalculoSimplesFragmentToListaEntregaSimplesFragment()
                findNavController().navigate(action)
            }
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
       binding.carregamento.isVisible = isLoading
    }

    private fun showErro(text: String) {
        var view = binding.root.rootView
        val snackBarView = Snackbar.make(view, text , Snackbar.LENGTH_LONG)
        view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        view.layoutParams = params
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()

        showLoading(false)
    }


    private fun calculoTotalSimples(contextTela : Context = requireContext(),entregaSimples:EntregaSimples,tipoTela:Int){

        val builder = AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.total_custo_calc_simples,null)

        val tvValorCalculado = view.findViewById<TextView>(R.id.tv_valor_cobrado)
        val tvDescriCalculoSimples = view.findViewById<TextView>(R.id.tv_tipo_calculo_simples)
        val tvDespesaCalculoSimples = view.findViewById<TextView>(R.id.tv_despesas_extras)
        val tiTitulo = view.findViewById<TextInputEditText>(R.id.ti_titulo)
        val tiValorCobrado = view.findViewById<TextInputEditText>(R.id.ti_valor_cobrado)

        if(!entregaSimples.valido()) {
            binding.tiInKmPercorrido.error = "Informe o total de kilometros que será percorrido."
            binding.tiInValorKmInformado.error = "Informe o valor cobrado por kilometro."
        }else{
            tiTitulo.setText(entregaSimples.titulo)

            if(entregaSimples.valorEntrega>0){
                tiValorCobrado.setText(entregaSimples.valorEntrega.toString())
            }

            tvValorCalculado.setText("Valor Calculado R$: ${entregaSimples.valorCalculado()}")
            tvDescriCalculoSimples.setText("${entregaSimples.descricaoCalculo()}")
            tvDespesaCalculoSimples.text = "Despesas extras = ${entregaSimples.valorDespExtra}"

            builder.setView(view)
                .setTitle("")

            builder.setPositiveButton("Ok") { dialog, which -> }

            builder.setNegativeButton("Cancelar", null)


            val dialog = builder.create()

            dialog.setOnShowListener {
                val button = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                button.setOnClickListener {

                    val titulo = tiTitulo.text.toString()
                    val valorEntrega = tiValorCobrado.text.toString()

                    if (titulo.isEmpty()) {
                        val erro = "Digite um titulo"
                        tiTitulo.error = erro
                    } else {

                        entregaSimples.titulo = titulo
                        entregaSimples.valorEntrega =
                            if (valorEntrega.isEmpty()) 0.0 else valorEntrega.toDouble()

                        // entregaSimples.valorEntrega =
                        if (tipoTela == 0) {
                            viewModel.addEntregaRota(entregaSimples)
                        } else if (tipoTela == 1) {
                            viewModel.updateEntregaRota(entregaSimples)
                        }

                        dialog.dismiss()

                    }
                }
            }

            dialog.show()
        }
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

    private fun setDadosTela(entregaSimples: EntregaSimples,bloqueio :Boolean = false){
        binding.tiInKmPercorrido.setText(entregaSimples.totalKm.toString())
        binding.tiInValorKmInformado.setText(entregaSimples.valorInformado.toString())
        binding.tiOutraDespesa.setText(entregaSimples.valorDespExtra.toString())
        binding.tiValorTipo.setText(entregaSimples.valorTpCalc.toString())
        if(entregaSimples.tipoCalc >=0){
            (binding.tiOpcao.editText as? AutoCompleteTextView)?.selectItem(items[entregaSimples.tipoCalc],entregaSimples.tipoCalc)
            binding.tiValorTipo.visibility = View.VISIBLE
        }
        tipoCalculo = entregaSimples.tipoCalc
        binding.tvValorKm.text = "Valor calculado R$: ${entregaSimples.valorEntregaCalculado}"

        if(bloqueio){
            binding.tiInKmPercorrido.isEnabled = false
            binding.tiInValorKmInformado.isEnabled = false
            binding.tiOutraDespesa.isEnabled = false
            binding.tiValorTipo.isEnabled = false
            binding.tiOpcao.isEnabled = false
        }

    }

    fun AutoCompleteTextView.selectItem(text: String, position: Int = 0) {
        this.setText(text)
        //this.showDropDown()
        this.setSelection(position)
        this.listSelection = position
       // this.performCompletion()
    }
}