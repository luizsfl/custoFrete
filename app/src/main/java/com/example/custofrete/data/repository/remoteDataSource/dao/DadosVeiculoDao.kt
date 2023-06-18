package com.example.custofrete.data.repository.remoteDataSource.dao

import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.presentation.config.ConfiguracaoFirebase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DadosVeiculoDao (
    private val autenticacaFirestore: FirebaseFirestore = ConfiguracaoFirebase.getFirebaseFirestore(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
){

    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo> {
        return flow {
            try {

                var messengerErro = ""

                autenticacaFirestore.collection("dadosVeiculo")
                    .document(dadosVeiculo.idUsuario)
                    .set(dadosVeiculo)
                    .addOnFailureListener {
                        messengerErro = "addDadosVeiculo3 $it.message.toString()"
                    }

                if(messengerErro.isEmpty()){
                    emit(dadosVeiculo)
                }else{
                    emit(error("addDadosVeiculo1 $messengerErro"))
                }
            } catch (e: Exception) {
                emit(error("addDadosVeiculo2 $+e.toString()"))
            }
        }.flowOn(dispatcher)
    }

}