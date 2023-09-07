package com.programacustofrete.custofrete.domain.useCase.veiculo

import com.programacustofrete.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoGetUseCase(
    private val dadosVeiculoRepository: DadosVeiculoRepository
) {
    operator fun invoke() = dadosVeiculoRepository.getDadosVeiculo()
}