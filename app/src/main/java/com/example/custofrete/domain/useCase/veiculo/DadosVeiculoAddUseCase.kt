package com.example.custofrete.domain.useCase.veiculo

import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.repository.DadosVeiculoRepository

class DadosVeiculoAddUseCase(
    private val dadosVeiculoRepository: DadosVeiculoRepository
) {
    operator fun invoke(dadosVeiculo: DadosVeiculo) =
        dadosVeiculoRepository.addDadosVeiculo(dadosVeiculo)
}