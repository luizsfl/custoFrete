package com.example.custofrete.presentation.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.show()

        binding.btCalculoSimples.setOnClickListener {
            val action =  HomeFragmentDirections.actionHomeFragmentToCalculoSimplesFragment()
            findNavController().navigate(action)
        }

//        binding.btCalculoAntt.setOnClickListener {
//            val action =  HomeFragmentDirections.actionHomeFragmentToCalculoANTTFragment()
//            findNavController().navigate(action)
//        }

        binding.btCalculoRotas.setOnClickListener {
            val action =  HomeFragmentDirections.actionHomeFragmentToDadosVeiculoFragment(2)
            findNavController().navigate(action)
        }

        return root
    }

}