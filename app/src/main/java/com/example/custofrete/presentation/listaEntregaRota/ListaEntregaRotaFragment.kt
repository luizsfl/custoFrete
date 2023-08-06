package com.example.custofrete.presentation.listaEntregaRota

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.databinding.FragmentListaEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.presentation.ViewStateDadosVeiculo
import com.example.custofrete.presentation.ViewStateEntregaRota
import com.example.custofrete.presentation.adapter.EntregaRotaAdapter
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaEntregaRotaFragment : Fragment() {

    private var _binding: FragmentListaEntregaRotaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListaEntregaRotaViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListaEntregaRotaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        viewModel.viewStateListEntregaRota.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateEntregaRota.Loading -> showLoading(viewState.loading)
                is ViewStateEntregaRota.sucessoGetAll -> setAdapter(viewState.listEntrega)
                is ViewStateEntregaRota.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }

        viewModel.getAllEntregaRota()

        binding.faNewEntregaRota.setOnClickListener {
            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToDadosVeiculoFragment(2)
            findNavController().navigate(action)
        }

        binding.ivVoltar.setOnClickListener{
            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        return root
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

    private fun setAdapter(listEntregaRota: List<Entrega>) {
        val rotaAdapter = EntregaRotaAdapter(listEntregaRota)
        rotaAdapter.onItemClick = {
            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToDadosRotaFragment2(it)
            findNavController().navigate(action)
        }

        rotaAdapter.onItemClickExcluir = {
            viewModel.deleteEntregaRota(it)
        }


        binding.recyclerview.adapter = rotaAdapter
        showLoading(false)
    }

}