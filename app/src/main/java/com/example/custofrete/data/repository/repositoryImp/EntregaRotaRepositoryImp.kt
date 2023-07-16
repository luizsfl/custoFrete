package com.example.custofrete.data.repository.repositoryImp

import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.repository.EntregaRotaRepository
import kotlinx.coroutines.flow.Flow

class EntregaRotaRepositoryImp(
    private val entregaRotaDataSource: EntregaRotaDataSource
): EntregaRotaRepository {
    override fun addEntregaRota(entrega: Entrega): Flow<Entrega> = entregaRotaDataSource.addEntregaRota(entrega)
}