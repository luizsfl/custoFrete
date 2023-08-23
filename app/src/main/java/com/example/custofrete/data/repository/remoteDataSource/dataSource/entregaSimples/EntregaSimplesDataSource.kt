package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples
import kotlinx.coroutines.flow.Flow

interface EntregaSimplesDataSource {
    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
//    fun getAllEntregaRota(): Flow<List<EntregaSimples>>
//    fun deleteEntregaRota(entrega: EntregaSimples): Flow<EntregaSimples>
//    fun updateEntregaRota(idDocument:String, listaRotas:List<EntregaSimples>, tipoTela:Int): Flow<List<EntregaSimples>>
}