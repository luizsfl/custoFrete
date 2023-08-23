package com.example.custofrete.domain.repository

import com.example.custofrete.domain.model.EntregaSimples
import kotlinx.coroutines.flow.Flow

interface EntregaSimplesRepository {
    fun addEntregaSimples(entrega: EntregaSimples): Flow<EntregaSimples>
}