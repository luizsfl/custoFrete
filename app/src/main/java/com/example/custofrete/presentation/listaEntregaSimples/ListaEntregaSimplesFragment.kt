package com.example.custofrete.presentation.listaEntregaSimples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custofrete.databinding.FragmentListaEntregaSimplesBinding

class ListaEntregaSimplesFragment : Fragment() {

    private var _binding: FragmentListaEntregaSimplesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaEntregaSimplesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.recyclerview.layoutManager = LinearLayoutManager(context)

        binding.faNewEntregaRota.setOnClickListener {
            val action =  ListaEntregaSimplesFragmentDirections.actionListaEntregaSimplesFragmentToCalculoSimplesFragment()
            findNavController().navigate(action)
        }

        return root

    }

}