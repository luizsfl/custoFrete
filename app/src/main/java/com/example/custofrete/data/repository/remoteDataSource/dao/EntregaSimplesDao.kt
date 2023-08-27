package com.example.custofrete.data.repository.remoteDataSource.dao

import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn


class EntregaSimplesDao (
    private val autenticacaFirestore: FirebaseFirestore = ConfiguracaoFirebase.getFirebaseFirestore(),
    private val autenticacao: FirebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
        = callbackFlow {
            try {
                var messengerErro = ""

                entrega.idUsuario = autenticacao.currentUser?.uid.toString()
                if(entrega.idDocument.isEmpty()){
                    autenticacaFirestore.collection("entregaSimples")
                        .add(entrega)
                        .addOnFailureListener {
                            messengerErro = "addEntregaSimples 1 ${it.message.toString()}"
                            trySend(error(messengerErro))
                        }.addOnSuccessListener { result ->
                            entrega.idDocument = result.id
                            trySend(entrega)
                        }
                }else{
                    
                    autenticacaFirestore.collection("entregaSimples")
                        .document(entrega.idDocument)
                        .set(entrega)
                        .addOnFailureListener {
                            messengerErro = "UpdateEntregaSimples 2 ${it.message.toString()}"
                            trySend(error(messengerErro))
                        }
                        .addOnSuccessListener {
                            trySend(entrega)
                        }
                }

                awaitCancellation()
            } catch (e: Exception) {
                trySend(error("addEntregaSimplesErro ${e.message.toString()}"))
            }

        awaitClose{
            close()
        }

        }.flowOn(dispatcher)

    fun getAllEntregaSimples(): Flow<List<EntregaSimples>> {
        return callbackFlow  {
                val idUsuario = autenticacao.currentUser?.uid.toString()

              autenticacaFirestore.collection("entregaSimples")
                  .whereEqualTo("idUsuario",idUsuario)
                    .get()
                    .addOnSuccessListener { result ->

                        val listEntregaRota = mutableListOf<EntregaSimples>()

                        for (document in result) {
                            val entregaSimples = document.toObject(EntregaSimples::class.java)!!
                            entregaSimples.idDocument = document.id

                            listEntregaRota.add(entregaSimples)
                        }

                        trySend(listEntregaRota)

                    }
                    .addOnFailureListener {
                        val messengerErro = "getEntregaRota ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
            awaitClose{
                close()
            }
        }.flowOn(dispatcher)
    }

    fun deleteEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples> {
            return callbackFlow  {

                autenticacaFirestore.collection("entregaSimples")
                    .document(entrega.idDocument)
                    .delete()
                    .addOnSuccessListener { result ->
                        trySend(entrega)
                    }
                    .addOnFailureListener {
                        val messengerErro = "deleteEntregaSimples ${it.message.toString()}"
                        trySend(error(messengerErro))
                    }
                awaitClose{
                    close()
                }
            }.flowOn(dispatcher)
        }

    fun updateEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples> {
        return this.addEntregaSimples(entrega)
    }
}