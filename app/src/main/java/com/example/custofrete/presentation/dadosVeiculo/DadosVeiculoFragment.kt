package com.example.custofrete.presentation.dadosVeiculo

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.custofrete.databinding.FragmentDadosVeiculoBinding
import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.presentation.ViewStateDadosVeiculo
import com.example.custofrete.presentation.ViewStateUsuario
import com.example.custofrete.presentation.calculo.CalculoFragmentArgs
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.example.custofrete.presentation.login.LoginFragmentDirections
import com.example.custofrete.presentation.login.LoginViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class DadosVeiculoFragment : Fragment() {

    private var _binding: FragmentDadosVeiculoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DadosVeiculoViewModel by viewModel()
    private val args = navArgs<DadosVeiculoFragmentArgs>()
    private var tipoTela = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDadosVeiculoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        tipoTela = args.value.tipoTela

        
        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextDadosVeiculos.setOnClickListener {
            val placa = binding.tiPlaca.editText?.text.toString()
            val kmLitro = if (binding.tiKmDadosVeiculo.text.toString().isEmpty()) 0.0 else binding.tiKmDadosVeiculo.text.toString().toDouble()
            val kmEixo = if (binding.tiQtdEixo.text.toString().isEmpty()) 0.0 else binding.tiQtdEixo.text.toString().toDouble()
            val peso = if (binding.tiPesoVeiculo.text.toString().isEmpty()) 0.0 else binding.tiPesoVeiculo.text.toString().toDouble()
            val idUsuario  = ConfiguracaoFirebase.getFirebaseAutenticacao().currentUser?.uid.toString()

            val dadosVeiculo = DadosVeiculo(placa,kmLitro,kmEixo,peso,idUsuario)
            if(validarDadosVeiculo(dadosVeiculo)){
                viewModel.addDadosVeiculo(dadosVeiculo)
            }

        }

        viewModel.viewStateDadosVeiculo.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateDadosVeiculo.Loading -> showLoading(viewState.loading)
                is ViewStateDadosVeiculo.sucessoDadosVeiculo  -> dadosVeiculoCriado(viewState.dadosVeiculo)
                is ViewStateDadosVeiculo.Failure -> showErro(viewState.messengerError)
                else -> {}
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

    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.isVisible = isLoading
    }
    private fun dadosVeiculoCriado(dadosVeiculo: DadosVeiculo){
        if(tipoTela == 1){
            val action =  DadosVeiculoFragmentDirections.actionDadosVeiculoFragmentToHomeFragment()
            findNavController().navigate(action)
        }else if (tipoTela == 2){
            showLoading(false)
            val entrega = Entrega(dadosVeiculo = dadosVeiculo, null,null)

            val action =  DadosVeiculoFragmentDirections.actionDadosVeiculoFragmentToCustoViagemFragment(entrega)
            findNavController().navigate(action)

        }

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
}