package com.programacustofrete.custofrete.domain.useCase.veiculo

import com.programacustofrete.custofrete.domain.model.DadosVeiculo
import com.programacustofrete.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoAddUseCase(
    private val dadosVeiculoRepository: DadosVeiculoRepository
) {
    operator fun invoke(dadosVeiculo: DadosVeiculo) =
        dadosVeiculoRepository.addDadosVeiculo(dadosVeiculo)
}