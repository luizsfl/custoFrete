package com.programacustofrete.custofrete.data.repository.repositoryImp

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSource
import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import com.programacustofrete.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoRepositoryImp(
    private val dadosVeiculoDataSource: DadosVeiculoDataSource
):DadosVeiculoRepository {
    override fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) = dadosVeiculoDataSource.addDadosVeiculo(dadosVeiculo)
    override fun getDadosVeiculo() = dadosVeiculoDataSource.getadosVeiculo()
}