package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo

import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import kotlinx.coroutines.flow.Flow

interface DadosVeiculoDataSource {
    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo>
    fun getadosVeiculo(): Flow<DadosVeiculo>

}