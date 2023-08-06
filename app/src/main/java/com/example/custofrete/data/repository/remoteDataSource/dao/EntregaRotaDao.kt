package com.example.custofrete.data.repository.remoteDataSource.dao

import android.annotation.SuppressLint
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
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

    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
        = callbackFlow {
            try {
                var messengerErro = ""
                entrega.idUsuario = entrega.dadosVeiculo.idUsuario

                autenticacaFirestore.collection("entrega")
                    .add(entrega)
                    .addOnFailureListener {
                        messengerErro = "addEntrega3 ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }.addOnSuccessListener { result ->
                        entrega.idDocument = result.id
                            trySend(entrega)
                        }
                awaitCancellation()
            } catch (e: Exception) {
                trySend(error("addEntregaErro ${e.message.toString()}"))
            }
        }.flowOn(dispatcher)

    fun getAllEntregaRota(): Flow<List<Entrega>> {
        return callbackFlow  {
                val idUsuario = autenticacao.currentUser?.uid.toString()

              autenticacaFirestore.collection("entrega")
                  .whereEqualTo("idUsuario",idUsuario)
                    .get()
                    .addOnSuccessListener { result ->

                        val listEntregaRota = mutableListOf<Entrega>()

                        for (document in result) {
                            val entregaRota = document.toObject(Entrega::class.java)!!
                            entregaRota.idDocument = document.id
                            listEntregaRota.add(entregaRota)
                        }

                        trySend(listEntregaRota)

                    }
                    .addOnFailureListener {
                        val messengerErro = "getEntregaRota ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
            awaitClose {}
        }.flowOn(dispatcher)
    }

    fun deleteEntregaRota(entrega: Entrega): Flow<Entrega> {
            return callbackFlow  {
                val idUsuario = autenticacao.currentUser?.uid.toString()

                autenticacaFirestore.collection("entrega")
                    .document(entrega.idDocument)
                    .delete()
                    .addOnSuccessListener { result ->

                        trySend(entrega)

                    }
                    .addOnFailureListener {
                        val messengerErro = "deleteEntregaRota ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
                awaitClose {}
            }.flowOn(dispatcher)
        }


    fun updateEntregaRota(idDocument:String,listaRotas:List<Rota>): Flow<List<Rota>> {
        return callbackFlow  {

            autenticacaFirestore.collection("entrega")
                .document(idDocument)
                .update("listaRotas",listaRotas)
                .addOnSuccessListener { result ->

                    trySend(listaRotas)

                }
                .addOnFailureListener {
                    val messengerErro = "updateEntregaRota ${it.message.toString()}"
                    trySend(error(messengerErro))
                }
            awaitClose {}
        }.flowOn(dispatcher)
    }
}