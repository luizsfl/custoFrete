package com.example.custofrete.domain.useCase.veiculo

import com.example.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoGetUseCase(
    private val dadosVeiculoRepository: DadosVeiculoRepository
) {
    operator fun invoke() = dadosVeiculoRepository.getDadosVeiculo()
}