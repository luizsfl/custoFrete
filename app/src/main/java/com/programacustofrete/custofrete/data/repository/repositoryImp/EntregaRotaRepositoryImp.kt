package com.programacustofrete.custofrete.data.repository.repositoryImp

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.programacustofrete.custofrete.domain.model.Entrega
import com.programacustofrete.custofrete.domain.model.Rota
import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaRepositoryImp(
    private val entregaRotaDataSource: EntregaRotaDataSource
): EntregaRotaRepository {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaDataSource.addEntregaRota(entrega)
    override fun getAllEntregaRota() = entregaRotaDataSource.getAllEntregaRota()
    override fun deleteEntregaRotaDao(entrega: Entrega) = entregaRotaDataSource.deleteEntregaRota(entrega)
    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int)= entregaRotaDataSource.updateEntregaRota(idDocument,listaRotas,tipoTela)
    override fun updateEntregaRotaStatus(idDocument: String, status: String) = entregaRotaDataSource.updateEntregaRotaStatus(idDocument,status)
}