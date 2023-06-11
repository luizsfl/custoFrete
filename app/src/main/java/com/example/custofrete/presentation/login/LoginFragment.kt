package com.example.custofrete.presentation.login

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.custofrete.databinding.FragmentLoginBinding
import com.example.custofrete.presentation.MainActivity
import com.example.custofrete.presentation.ViewState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val root: View = binding.root

        (activity as AppCompatActivity).supportActionBar?.hide()

        loginViewModel.VerificarUserLogado()

        binding.txtTelaCadastro.setOnClickListener{
            val action =  LoginFragmentDirections.actionLoginFragmentToCadastroLoginFragment()
            findNavController().navigate(action)
        }

        loginViewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Loading -> showLoading(viewState.loading)
                is ViewState.Logado  -> usuarioLogado(viewState.usuarioLogado)
                is ViewState.Failure -> showErro(viewState.messengerError)
                else -> {}
            }
        }

        binding.btEntrar.setOnClickListener {
            val email = binding.editLogin.text.toString()
            val senha = binding.editSenha.text.toString()
            var mernsagem_erro = binding.mensageErro
            if (email.isEmpty() || senha.isEmpty())
            {
                mernsagem_erro.setText("Prencha os campos de e-mail e senha")
            }else{
                AutenticarUsuário(email,senha,mernsagem_erro)
            }
        }
        return root
    }

    private fun AutenticarUsuário(email:String,senha:String,mernsagem_erro: TextView){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener {
            if (it.isSuccessful){
                IrParaTelaPrincipal()
            }else{
                var erro = it

                when {
                    erro is FirebaseAuthInvalidCredentialsException -> mernsagem_erro.setText("E-mail ou senha estão incorretos")
                    erro is FirebaseNetworkException -> mernsagem_erro.setText("Sem conexão com internet")
                    else -> mernsagem_erro.setText("Erro ao tentar logar"+it.exception)
                }
            }
        }
    }

    private fun IrParaTelaPrincipal(){
        val builder = AlertDialog.Builder(requireContext())
        with(builder)
        {
            setTitle("Login realizado com sucesso")
            setCancelable(false) //não fecha quando clicam fora do dialog
            setPositiveButton("OK") { dialog, which ->
                val action =  LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }
            show()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun usuarioLogado(logado:Boolean){
        if(logado){
            val action =  LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            findNavController().navigate(action)
        }
        showLoading(false)
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

}