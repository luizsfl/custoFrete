package com.example.custofrete.presentation.cadastroLogin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.custofrete.databinding.ActivityCadastroLoginBinding
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.presentation.ViewState
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroLoginActivity : AppCompatActivity() {

    private val cadastroLoginViewModel: CadastroLoginViewModel by viewModel()

    private lateinit var usuario : Usuario

    private lateinit var binding: ActivityCadastroLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Cadastro"

        binding.butonCadastrar.setOnClickListener {
            val textoNome = binding.editNome.text.toString()
            val textoEmail = binding.editEmail.text.toString()
            val textoSenha = binding.editSenha.text.toString()

            usuario = Usuario(textoNome,textoEmail,textoSenha,"")
            cadastrarUsuario(usuario)
        }

        cadastroLoginViewModel.viewState.observe(this) { viewState ->
            when (viewState) {
                is ViewState.Loading -> showLoading(viewState.loading)
                is ViewState.sucessoUsuario -> sucessoUsuario(viewState.usuario)
                is ViewState.Failure -> showErro(viewState.messengerError)
            }
        }
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

        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Usuario: " +usuario.nome +" Criado com sucesso")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                finish()
            })
            show()
        }
    }

    fun cadastrarUsuario(usuario:Usuario){
        cadastroLoginViewModel.addUsuario(usuario)
    }
}