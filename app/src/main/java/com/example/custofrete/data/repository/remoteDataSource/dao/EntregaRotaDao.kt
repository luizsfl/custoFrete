package com.example.custofrete.data.repository.remoteDataSource.dao

import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class EntregaRotaDao (
    private val autenticacaFirestore: FirebaseFirestore = ConfiguracaoFirebase.getFirebaseFirestore(),
    private val autenticacao: FirebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun addEntregaRota(entrega: Entrega): Flow<Entrega> {
        return flow {
            try {

                var messengerErro = ""
                entrega.idUsuario = entrega.dadosVeiculo.idUsuario

                autenticacaFirestore.collection("entrega")
                    .document()
                    .set(entrega)
                    .addOnFailureListener {
                        messengerErro = "addEntrega3 ${it.message.toString()}"
                    }

                if (messengerErro.isEmpty()) {
                    emit(entrega)
                } else {
                    emit(error("addEntrega1 $messengerErro"))
                }
            } catch (e: Exception) {
                emit(error("addEntrega2 ${e.message.toString()}"))
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
                        }
                    }
                    .addOnFailureListener {
                        val messengerErro = "getDadosVeiculo ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
            awaitClose {}
        }.flowOn(dispatcher)
    }

}