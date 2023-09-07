package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.DadosVeiculoDao
import com.programacustofrete.custofrete.domain.model.DadosVeiculo

class DadosVeiculoDataSourceImp(
    private val dadosVeiculoDao: DadosVeiculoDao
) : DadosVeiculoDataSource {
    override fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) = dadosVeiculoDao.addDadosVeiculo(dadosVeiculo)

    override fun getadosVeiculo() = dadosVeiculoDao.getDadosVeiculo()

}