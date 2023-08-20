package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota

import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota

class EntregaRotaDataSourceImp(
    private val entregaRotaDao: EntregaRotaDao
) : EntregaRotaDataSource {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaDao.addEntregaRota(entrega)
    override fun getAllEntregaRota() = entregaRotaDao.getAllEntregaRota()
    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDao.deleteEntregaRota(entrega)
    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) = entregaRotaDao.updateEntregaRota(idDocument,listaRotas,tipoTela)
}