package com.programacustofrete.custofrete.domain.useCase.veiculo

import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import kotlinx.coroutines.flow.Flow

interface DadosVeiculoInteractor {
    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo): Flow<DadosVeiculo>
    fun getDadosVeiculo(): Flow<DadosVeiculo>

}