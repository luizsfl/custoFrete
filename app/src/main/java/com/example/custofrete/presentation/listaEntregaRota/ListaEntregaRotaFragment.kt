package com.example.custofrete.presentation.listaEntregaRota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.databinding.FragmentListaEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.presentation.ViewStateDadosVeiculo
import com.example.custofrete.presentation.ViewStateEntregaRota
import com.example.custofrete.presentation.adapter.EntregaRotaAdapter
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
                is ViewStateEntregaRota.sucessoGetAll -> setAdapter(viewState.listEntrega)
                else -> {}
            }
        }

        viewModel.getAllEntregaRota()

        binding.faNewEntregaRota.setOnClickListener {
            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToDadosVeiculoFragment(2)
            findNavController().navigate(action)
        }

        return root
    }

    private fun setAdapter(listEntregaRota: List<Entrega>) {
        val rotaAdapter = EntregaRotaAdapter(listEntregaRota)
        rotaAdapter.onItemClick = {
//            val intent = Intent(requireContext(), HomeDetailActivity::class.java)
//                .apply {
//                    putExtra("idCaixa", it.idCaixa)
//                }
//            startActivity(intent)
        }

        binding.recyclerview.adapter = rotaAdapter
    }

}