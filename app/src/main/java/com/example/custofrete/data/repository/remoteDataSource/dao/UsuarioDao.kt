package com.example.custofrete.data.repository.remoteDataSource.dao

import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.example.custofrete.presentation.helper.Base64Custom
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UsuarioDao(
    private val autenticacao: FirebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun addUsuario(usuario: Usuario): Flow<Usuario> {
        return flow {
            try {

                var messengerErro = ""

                autenticacao.createUserWithEmailAndPassword(
                    usuario.email,usuario.senha
                ).addOnCompleteListener{
                    if (it.isSuccessful) {

                        //cadastrar o email e senha no fire auth
                        var  idUsuario = Base64Custom.codificarBase64(usuario.email)
                        usuario.idUsuario = idUsuario
                        salvarUsuser(usuario)

                    }else{
                        messengerErro = it.exception.toString()
                    }
                }.addOnFailureListener {
                   messengerErro = when {
                        it is FirebaseAuthWeakPasswordException ->  "Digite uma senha com no minimo 6 caracteres"
                        it is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
                        it is FirebaseNetworkException -> "Usuário sem acesso a internet"
                        else -> "Erro ao cadastrar"+it.toString()
                    }

                }

                if(messengerErro.isEmpty()){
                    emit(usuario)
                }else{
                    emit(error("UserDao1"+messengerErro))
                }
            } catch (e: Exception) {
                emit(error("UserDao"+e.toString()))
            }
        }.flowOn(dispatcher)
    }

    //Função que salva o usuário na tabela do firebase
    fun salvarUsuser(usuario: Usuario) {

        val db = ConfiguracaoFirebase.getFirebaseFirestore()

        db.collection("usuarios")
            .document(usuario.idUsuario)
            .set(usuario)
    }

}