package com.example.custofrete.data.repository.repositoryImp

import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaRepositoryImp(
    private val entregaRotaDataSource: EntregaRotaDataSource
): EntregaRotaRepository {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaDataSource.addEntregaRota(entrega)
    override fun getAllEntregaRota() = entregaRotaDataSource.getAllEntregaRota()
    override fun deleteEntregaRotaDao(entrega: Entrega) = entregaRotaDataSource.deleteEntregaRota(entrega)
    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>)= entregaRotaDataSource.updateEntregaRota(idDocument,listaRotas)
}