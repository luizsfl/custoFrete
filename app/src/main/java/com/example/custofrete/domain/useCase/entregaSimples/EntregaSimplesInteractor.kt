package com.example.custofrete.domain.useCase.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples
import kotlinx.coroutines.flow.Flow


interface EntregaSimplesInteractor {
    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
//    fun getAllEntregaRota(): Flow<List<Entrega>>
//    fun deleteEntregaRota(entrega: Entrega): Flow<Entrega>
//    fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>, tipoTela:Int): Flow<List<Rota>>

}