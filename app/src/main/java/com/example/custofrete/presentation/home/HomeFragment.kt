package com.example.custofrete.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentHomeBinding
import com.example.custofrete.presentation.MainActivity
import com.example.custofrete.presentation.login.LoginActivity
import com.example.custofrete.presentation.rotas.RotasFragmentDirections

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.novaEntrega.setOnClickListener {
            val action =  HomeFragmentDirections.actionHomeFragmentToDadosVeiculoFragment()
            findNavController().navigate(action)
        }

        binding.btLogin.setOnClickListener {
            val intent = Intent(container?.context, LoginActivity::class.java)
            startActivity(intent)
        }

        return root

    }

}