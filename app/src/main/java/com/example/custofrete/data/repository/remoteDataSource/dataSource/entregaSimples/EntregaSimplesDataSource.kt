package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples
import kotlinx.coroutines.flow.Flow

interface EntregaSimplesDataSource {
    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
    fun getAllEntregaSimples(): Flow<List<EntregaSimples>>
    fun deleteEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
    fun updateEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
}