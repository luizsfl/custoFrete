package com.example.custofrete.data.repository.repositoryImp

import com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSource
import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoRepository(
    private val dadosVeiculoDataSource: DadosVeiculoDataSource
):DadosVeiculoRepository {
    override fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) = dadosVeiculoDataSource.addDadosVeiculo(dadosVeiculo)
}