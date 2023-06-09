package com.example.custofrete.presentation.entrega

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.R
import com.example.custofrete.databinding.FragmentEntregaBinding

class EntregaFragment : Fragment() {

    private var _binding: FragmentEntregaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntregaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btDadosVeiculo.setOnClickListener {
            dadosVeiculo(requireContext())
        }

        binding.btCusto.setOnClickListener {
            custoViagem(requireContext())
        }

        binding.btRotas.setOnClickListener {
            dadosRota(requireContext())
        }

        binding.btCalcular.setOnClickListener {
            val action = EntregaFragmentDirections.actionEntregaFragmentToCalculoFragment()
            findNavController().navigate(action)
        }

        return root
    }

    private fun dadosVeiculo(contextTela : Context){

        val builder = AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.dialogo_dados_veiculo,null)

       // val spState = view.findViewById<Spinner>(R.id.spStateSearch)


        builder.setView(view)

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)


        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {


//                if(position <= 0 ){
//                    val text = "Selecione uma cidade"
//                    tvCity.setError(text)
//                }else{
//                    dialog.dismiss()
//                }
            }
        }
        dialog.show()
    }


    private fun custoViagem(contextTela : Context){

        val builder = AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.dialogo_custos_viagem,null)

        // val spState = view.findViewById<Spinner>(R.id.spStateSearch)


        builder.setView(view)

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)


        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {


//                if(position <= 0 ){
//                    val text = "Selecione uma cidade"
//                    tvCity.setError(text)
//                }else{
//                    dialog.dismiss()
//                }
            }
        }
        dialog.show()
    }


    private fun dadosRota(contextTela : Context){

        val builder = AlertDialog.Builder(contextTela!!)

        val view: View
        val inflater = contextTela!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.dialogo_dados_rotas,null)

        // val spState = view.findViewById<Spinner>(R.id.spStateSearch)


        builder.setView(view)

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)


        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {


//                if(position <= 0 ){
//                    val text = "Selecione uma cidade"
//                    tvCity.setError(text)
//                }else{
//                    dialog.dismiss()
//                }
            }
        }
        dialog.show()
    }

}