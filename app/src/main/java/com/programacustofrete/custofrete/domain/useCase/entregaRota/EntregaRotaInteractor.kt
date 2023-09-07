package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.model.Entrega
import com.programacustofrete.custofrete.domain.model.Rota
import kotlinx.coroutines.flow.Flow

interface EntregaRotaInteractor {
    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
    fun getAllEntregaRota(): Flow<List<Entrega>>
    fun deleteEntregaRota(entrega: Entrega): Flow<Entrega>
    fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int): Flow<List<Rota>>
    fun entregaRotaUpdateStatusUseCase(idDocument: String, status:String): Flow<String>
 }