package com.example.custofrete.presentation.rotas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentRotasBinding

class RotasFragment : Fragment() {

    private var _binding: FragmentRotasBinding? = null
    private val binding get() = _binding!!

    val arrayOfStrings = arrayOf("alfhanistan","albania","alegri","andorra","angola")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRotasBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val arrayAdapter = ArrayAdapter(requireContext(),
            R.layout.autocomplete_list_rotas,
            R.id.tv_title_rota_list,
            arrayOfStrings.toList()
        )

        binding.btEnderecoEntrega.setAdapter(arrayAdapter)

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.nextRotas.setOnClickListener {
            val action = RotasFragmentDirections.actionRotasFragmentToCalculoFragment()
            findNavController().navigate(action)
        }

        return root
    }
}