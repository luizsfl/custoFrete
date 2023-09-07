package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.EntregaSimplesDao
import com.programacustofrete.custofrete.domain.model.EntregaSimples

class EntregaSimplesDataSourceImp(
    private val entregaSimplesDao: EntregaSimplesDao
) : EntregaSimplesDataSource {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.addEntregaSimples(entrega)
    override fun getAllEntregaSimples() = entregaSimplesDao.getAllEntregaSimples()
    override fun deleteEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.deleteEntregaSimples(entrega)
    override fun updateEntregaSimples(entrega: EntregaSimples) = entregaSimplesDao.updateEntregaSimples(entrega)
}