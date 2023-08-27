package com.example.custofrete.data.repository.repositoryImp

import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSource
import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesRepositoryImp(
    private val entregaSimplesDataSource: EntregaSimplesDataSource
):EntregaSimplesRepository {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesDataSource.addEntregaSimples(entrega)
    override fun getAllEntregaSimples()= entregaSimplesDataSource.getAllEntregaSimples()
}