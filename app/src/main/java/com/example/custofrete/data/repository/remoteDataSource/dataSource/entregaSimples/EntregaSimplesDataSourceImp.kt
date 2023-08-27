package com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples

import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaSimplesDao
import com.example.custofrete.domain.model.EntregaSimples

class EntregaSimplesDataSourceImp(
    private val entregaSimplesDao: EntregaSimplesDao
) : EntregaSimplesDataSource {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.addEntregaSimples(entrega)
    override fun getAllEntregaSimples() = entregaSimplesDao.getAllEntregaSimples()
    override fun deleteEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.deleteEntregaSimples(entrega)
    override fun updateEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.updateEntregaSimples(entrega)
}