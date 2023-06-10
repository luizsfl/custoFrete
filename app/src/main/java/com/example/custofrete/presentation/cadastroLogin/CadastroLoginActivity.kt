package com.example.custofrete.presentation.cadastroLogin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.custofrete.databinding.ActivityCadastroLoginBinding
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.example.custofrete.presentation.helper.Base64Custom
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class CadastroLoginActivity : AppCompatActivity() {

    private lateinit var autenticacao: FirebaseAuth

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
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao()
        autenticacao.createUserWithEmailAndPassword(
            usuario.email,usuario.senha
        ).addOnCompleteListener{
            if (it.isSuccessful) {
                //cadastrado
                var  idUsuario = Base64Custom.codificarBase64(usuario.email)
                usuario.idUsuario = idUsuario
                usuario.salvar()
                finish()
            }
        }.addOnFailureListener {
            when {
                it is FirebaseAuthWeakPasswordException -> Toast.makeText( this,"Digite uma senha com no minimo 6 caracteres", Toast.LENGTH_SHORT).show()
                it is FirebaseAuthUserCollisionException -> Toast.makeText( this,"E-mail já cadastrado", Toast.LENGTH_SHORT).show()
                it is FirebaseNetworkException -> Toast.makeText( this,"Usuário sem acesso a internet", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText( this,"Erro ao cadastrar"+it.toString(), Toast.LENGTH_SHORT).show()
            }

        }
    }
}