package com.example.custofrete.presentation.cadastroLogin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.databinding.FragmentCadastroLoginBinding
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.presentation.ViewStateUsuario
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroLoginFragment : Fragment() {

    private val cadastroLoginViewModel: CadastroLoginViewModel by viewModel()
    private lateinit var usuario : Usuario

    private var _binding: FragmentCadastroLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCadastroLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        binding.butonCadastrar.setOnClickListener {
            val textoNome = binding.editNome.text.toString()
            val textoEmail = binding.editEmail.text.toString()
            val textoSenha = binding.editSenha.text.toString()

            usuario = Usuario(nome = textoNome, email =  textoEmail,senha = textoSenha, idUsuario ="")
            cadastrarUsuario(usuario)
        }

        cadastroLoginViewModel.viewStateUsuario.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewStateUsuario.Loading -> showLoading(viewState.loading)
                is ViewStateUsuario.sucessoUsuario -> sucessoUsuario(viewState.usuario)
                is ViewStateUsuario.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }
        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun showErro(text: String) {
        var view = binding.root.rootView
        val snackBarView = Snackbar.make(view, text , Snackbar.LENGTH_LONG)
        view = snackBarView.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.CENTER
        view.layoutParams = params
        snackBarView.animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        snackBarView.show()

        showLoading(false)
    }

    fun sucessoUsuario(usuario:Usuario){

        showLoading(false)

        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Usuario: " +usuario.nome +" Criado com sucesso")
            setCancelable(false) //não fecha quando clicam fora do dialog
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                val action =  CadastroLoginFragmentDirections.actionCadastroLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            })
            show()
        }
    }

    fun cadastrarUsuario(usuario:Usuario){
        cadastroLoginViewModel.addUsuario(usuario)
    }
}