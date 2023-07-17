package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota

import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.example.custofrete.domain.model.Entrega

class EntregaRotaDataSourceImp(
    private val entregaRotaDao: EntregaRotaDao
) : EntregaRotaDataSource {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaDao.addEntregaRota(entrega)
    override fun getAllEntregaRota() = entregaRotaDao.getAllEntregaRota()
    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDao.deleteEntregaRota(entrega)
}