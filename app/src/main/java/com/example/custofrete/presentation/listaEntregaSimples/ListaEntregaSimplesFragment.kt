package com.example.custofrete.presentation.listaEntregaSimples

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.databinding.FragmentListaEntregaSimplesBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.presentation.ViewStateEntregaRota
import com.example.custofrete.presentation.ViewStateEntregaSimples
import com.example.custofrete.presentation.adapter.EntregaRotaAdapter
import com.example.custofrete.presentation.adapter.EntregaSimplesAdapter
import com.example.custofrete.presentation.calculoRota.CalculoRotaFragmentArgs
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaFragmentDirections
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaEntregaSimplesFragment : Fragment() {

    private var _binding: FragmentListaEntregaSimplesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListaEntregaSimplesViewModel by viewModel()
    private val args = navArgs<CalculoRotaFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEntregaSimplesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        viewModel.viewStateListEntregaSimples.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateEntregaSimples.Loading -> showLoading(viewState.loading)
                is ViewStateEntregaSimples.sucessoGetAll -> setAdapter(viewState.listEntrega)
                is ViewStateEntregaSimples.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }

        binding.faNewEntregaRota.setOnClickListener {
            val action =  ListaEntregaSimplesFragmentDirections.actionListaEntregaSimplesFragmentToCalculoSimplesFragment()
            findNavController().navigate(action)
        }

        viewModel.getAllEntregaSimples()

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

    private fun setAdapter(listEntregaSimples: List<EntregaSimples>) {
        val rotaAdapter = EntregaSimplesAdapter(listEntregaSimples)

        if (listEntregaSimples.size== 0){
            binding.txtSemEntregas.visibility = View.VISIBLE
            binding.txtTitulo.visibility = View.GONE
        }else{
            binding.txtSemEntregas.visibility = View.GONE
            binding.txtTitulo.visibility = View.VISIBLE
        }

        rotaAdapter.onItemClick = {
//            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToDadosRotaFragment2(it)
//            findNavController().navigate(action)
        }


        rotaAdapter.onItemClickEditar = {
            val action =  ListaEntregaSimplesFragmentDirections.actionListaEntregaSimplesFragmentToCalculoSimplesFragment(it,1)
            findNavController().navigate(action)
        }

        rotaAdapter.onItemClickVisualizar = {
            val action =  ListaEntregaSimplesFragmentDirections.actionListaEntregaSimplesFragmentToCalculoSimplesFragment(it,2)
            findNavController().navigate(action)
        }

        rotaAdapter.onItemClickExcluir = {
            excluirEntrega(requireContext(),it)
        }

        binding.recyclerview.adapter = rotaAdapter
        showLoading(false)
    }

    private fun excluirEntrega(contextTela : Context, entrega: EntregaSimples){

        val builder = AlertDialog.Builder(contextTela!!)

        builder.setTitle("Deseja realmente excluir ?? ")

        builder.setPositiveButton("Sim") { dialog, which ->
            viewModel.deleteEntregaSimples(entrega)
        }

        builder.setNegativeButton("Não", null)

        builder.create()

        builder.show()

    }

}