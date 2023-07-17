package com.example.custofrete.presentation.dadosEntregaRota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.databinding.FragmentDadosEntregaRotaBinding
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.adapter.EntregaRotaPendenteAdapter
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaFragmentDirections

class DadosEntregaRotaFragment : Fragment() {

    private var _binding: FragmentDadosEntregaRotaBinding? = null
    private val binding get() = _binding!!
    private val args = navArgs<DadosEntregaRotaFragmentArgs>()
    private lateinit var entrega: Entrega

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDadosEntregaRotaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        entrega = args.value.entrega

        if(entrega.listaRotas?.size!! > 0){
            setAdapterPendente(entrega.listaRotas!!)
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        binding.ivListaEntrega.setOnClickListener {
            val action =  DadosEntregaRotaFragmentDirections.actionDadosRotaFragmentToListaEntregaRotaFragment()
            findNavController().navigate(action)
        }

        binding.ivVoltar.setOnClickListener{
            val action =  DadosEntregaRotaFragmentDirections.actionDadosRotaFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        (activity as AppCompatActivity).supportActionBar?.hide()

        return root
    }

    private fun setAdapterPendente(listEntregaRota: List<Rota>) {
        val rotaAdapter = EntregaRotaPendenteAdapter(listEntregaRota)
//        rotaAdapter.onItemClick = {
//            val action =  ListaEntregaRotaFragmentDirections.actionListaEntregaRotaFragmentToDadosRotaFragment2(it)
//            findNavController().navigate(action)
//        }

        binding.recyclerview.adapter = rotaAdapter
    }

}