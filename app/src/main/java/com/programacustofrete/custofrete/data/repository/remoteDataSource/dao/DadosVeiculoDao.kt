package com.programacustofrete.custofrete.data.repository.remoteDataSource.dao

import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import com.programacustofrete.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class DadosVeiculoDao (
    private val autenticacaFirestore: FirebaseFirestore = ConfiguracaoFirebase.getFirebaseFirestore(),
    private val autenticacao: FirebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo> {
        return flow {
            try {

                var messengerErro = ""

                autenticacaFirestore.collection("dadosVeiculo")
                    .document(dadosVeiculo.idUsuario)
                    .set(dadosVeiculo)
                    .addOnFailureListener {
                        messengerErro = "addDadosVeiculo3 ${it.message.toString()}"
                    }

                if (messengerErro.isEmpty()) {
                    emit(dadosVeiculo)
                } else {
                    emit(error("addDadosVeiculo1 $messengerErro"))
                }
            } catch (e: Exception) {
                emit(error("addDadosVeiculo2 ${e.message.toString()}"))
            }
        }.flowOn(dispatcher)
    }


    fun getDadosVeiculo(): Flow<DadosVeiculo> {
        return callbackFlow  {
                val idUsuario = autenticacao.currentUser?.uid.toString()

              autenticacaFirestore.collection("dadosVeiculo")
                    .document(idUsuario)
                    .get()
                    .addOnSuccessListener { documento ->
                        if (documento != null && documento.exists()) {
                            val dadosVeiculo = documento.toObject(DadosVeiculo::class.java)!!
                            trySend(dadosVeiculo)
                        }else{
                            trySend(DadosVeiculo())
                        }

                    }
                    .addOnFailureListener {
                        val messengerErro = "getDadosVeiculo ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
            awaitClose{
                close()
            }
        }.flowOn(dispatcher)
    }

}