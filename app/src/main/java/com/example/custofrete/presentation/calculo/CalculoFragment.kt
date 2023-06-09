package com.example.custofrete.presentation.calculo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.custofrete.databinding.FragmentCalculoBinding

class CalculoFragment : Fragment() {

    private var _binding: FragmentCalculoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root

    }
}