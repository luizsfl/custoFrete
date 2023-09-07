package com.programacustofrete.custofrete.data.repository.repositoryImp

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSource
import com.programacustofrete.custofrete.domain.model.EntregaSimples
import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesRepositoryImp(
    private val entregaSimplesDataSource: EntregaSimplesDataSource
):EntregaSimplesRepository {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesDataSource.addEntregaSimples(entrega)
    override fun getAllEntregaSimples() = entregaSimplesDataSource.getAllEntregaSimples()
    override fun deleteEntregaSimples(entrega: EntregaSimples) = entregaSimplesDataSource.deleteEntregaSimples(entrega)
    override fun updateEntregaSimples(entrega: EntregaSimples) = entregaSimplesDataSource.updateEntregaSimples(entrega)
}