package com.example.custofrete.domain.repository

import com.example.custofrete.domain.model.Entrega
import kotlinx.coroutines.flow.Flow

interface EntregaRotaRepository {
    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
    fun getAllEntregaRota(): Flow<List<Entrega>>
}