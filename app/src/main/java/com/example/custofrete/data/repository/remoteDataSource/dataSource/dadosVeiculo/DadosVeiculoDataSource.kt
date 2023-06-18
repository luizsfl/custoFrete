package com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo

import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface DadosVeiculoDataSource {
    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo>
}