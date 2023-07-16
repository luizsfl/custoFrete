package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota

import com.example.custofrete.domain.model.Entrega
import kotlinx.coroutines.flow.Flow


interface EntregaRotaDataSource {
    fun addEntregaRota(entrega: Entrega): Flow<Entrega>
}