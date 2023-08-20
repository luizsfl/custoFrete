package com.example.custofrete.domain.repository

import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import kotlinx.coroutines.flow.Flow

interface EntregaRotaRepository {
    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
    fun getAllEntregaRota(): Flow<List<Entrega>>
    fun deleteEntregaRotaDao(entrega: Entrega): Flow<Entrega>
    fun updateEntregaRota(idDocument:String,listaRotas:List<Rota>,tipoTela:Int): Flow<List<Rota>>
}