package com.example.custofrete.presentation.cadastroLogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.custofrete.databinding.ActivityCadastroLoginBinding
import com.example.custofrete.domain.model.Usuario
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

            if (!textoNome.isEmpty()){
                if (!textoEmail.isEmpty()){
                    if (!textoSenha.isEmpty()){
                        //Caso todos os campos estejam preenchidos
                        usuario = Usuario(textoNome,textoEmail,textoSenha,"")
                        cadastrarUsuario(usuario)
                    }else{
                        Toast.makeText( this,"Preencha a senha", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText( this,"Preencha o E-mail", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText( this,"Preencha o nome", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun cadastrarUsuario(usuario:Usuario){
        cadastroLoginViewModel.addUsuario(usuario)
    }
}