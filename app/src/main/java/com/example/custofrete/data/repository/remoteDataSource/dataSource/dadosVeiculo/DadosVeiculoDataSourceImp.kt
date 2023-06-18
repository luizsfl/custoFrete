package com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo

import com.example.custofrete.domain.model.DadosVeiculo

class DadosVeiculoDataSourceImp(
    private val dadosVeiculoDataSource: DadosVeiculoDataSource
) : DadosVeiculoDataSource {
    override fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) = dadosVeiculoDataSource.addDadosVeiculo(dadosVeiculo)
}