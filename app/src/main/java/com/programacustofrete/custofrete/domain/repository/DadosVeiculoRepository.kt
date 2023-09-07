package com.programacustofrete.custofrete.domain.repository

import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import kotlinx.coroutines.flow.Flow

interface DadosVeiculoRepository {
    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo>
    fun getDadosVeiculo(): Flow<DadosVeiculo>

}