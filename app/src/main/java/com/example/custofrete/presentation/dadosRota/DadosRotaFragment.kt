package com.example.custofrete.presentation.dadosRota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentDadosRotaBinding


class DadosRotaFragment : Fragment() {

    private var _binding: FragmentDadosRotaBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dados_rota, container, false)
    }

}