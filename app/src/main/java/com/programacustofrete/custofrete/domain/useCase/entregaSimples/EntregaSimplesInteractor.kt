package com.programacustofrete.custofrete.domain.useCase.entregaSimples

import com.programacustofrete.custofrete.domain.model.EntregaSimples
import kotlinx.coroutines.flow.Flow

interface EntregaSimplesInteractor {
    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
    fun getAllEntregaSimples(): Flow<List<EntregaSimples>>
    fun deleteEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
    fun updateEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
}