package com.example.custofrete.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.custofrete.databinding.ActivityLoginBinding
import com.example.custofrete.presentation.MainActivity
import com.example.custofrete.presentation.cadastroLogin.CadastroLoginActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        VerificarUserLogado()

        binding.txtTelaCadastro.setOnClickListener{
            val intent = Intent(this, CadastroLoginActivity::class.java)
            startActivity(intent)
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
    }

    private fun AutenticarUsuário(email:String,senha:String,mernsagem_erro: TextView){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this,"Login efetuado com sucesso",Toast.LENGTH_SHORT).show()
                IrParaTelaPrincipal()
            }else{
                var erro = it

                when {
                    erro is FirebaseAuthInvalidCredentialsException -> mernsagem_erro.setText("E-mail ou senha estão incorretos")
                    erro is FirebaseNetworkException -> mernsagem_erro.setText("Sem conexão com internet")
                    else -> mernsagem_erro.setText("Erro ao tentar logar"+it)

                }
            }
        }
    }

    private fun VerificarUserLogado(){
        val usuarioLogado = FirebaseAuth.getInstance().currentUser
        if(usuarioLogado!=null){
            IrParaTelaPrincipal()
        }
    }

    private fun IrParaTelaPrincipal(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}