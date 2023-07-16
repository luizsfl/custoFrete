package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota

import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.example.custofrete.domain.model.Entrega
import kotlinx.coroutines.flow.Flow

class EntregaRotaDataSourceImp(
    private val entregaRotaDao: EntregaRotaDao
) : EntregaRotaDataSource {
    override fun addEntregaRota(entrega: Entrega): Flow<Entrega> = entregaRotaDao.addEntregaRota(entrega)
}