package com.example.custofrete.presentation.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.databinding.FragmentHomeBinding

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
            val action =  HomeFragmentDirections.actionHomeFragmentToListaEntregaSimplesFragment()
            findNavController().navigate(action)
        }

//        binding.btCalculoAntt.setOnClickListener {
//            val action =  HomeFragmentDirections.actionHomeFragmentToCalculoANTTFragment()
//            findNavController().navigate(action)
//        }

        binding.btCalculoRotas.setOnClickListener {
            val action =  HomeFragmentDirections.actionHomeFragmentToListaEntregaRotaFragment()
            findNavController().navigate(action)

            /*
            val action =  HomeFragmentDirections.actionHomeFragmentToDadosVeiculoFragment(2)
            findNavController().navigate(action)
            */
        }

        binding.floatMessenger.setOnClickListener {
            showAlertDialogSendMensagem(requireContext())
        }


        return root
    }


    private fun showAlertDialogSendMensagem(contextTela: Context) {

        val builder = AlertDialog.Builder(contextTela)
        builder.setTitle("Envie uma mensagem, dúvida ou melhoria")

        val input = EditText(contextTela)
        input.setHint("Digite aqui")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which -> }

        builder.setNegativeButton("Cancel", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {

                var message = input.text.toString()

                if(message.isEmpty()){
                    val erro = "Digite a sua mensagem, dúvida ou melhoria. Campo ficou em branco"
                    input.error = erro
                }else{
                    val destinatario = "luizsfl+custofrete@hotmail.com"
                    val assunto = "Mensagem,duvida ou melhoria"

                    sendEmail(requireContext(),destinatario,assunto, message)
                    dialog.dismiss()

                }
            }
        }

        dialog.show()
    }

    private  fun  sendEmail(contextTela: Context, recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.setPackage("com.google.android.gm");

        mIntent.data = Uri.parse( "mailto:" )
        mIntent.type = "text/plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..." ))
        }
        catch (e: Exception){
            Toast.makeText( contextTela , e.message, Toast.LENGTH_LONG).show()
        }

    }

}