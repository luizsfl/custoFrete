package com.example.custofrete.domain.useCase.veiculo

import com.example.custofrete.domain.model.DadosVeiculo


class DadosVeiculoInteractorImp(
    private val dadosVeiculoAddUseCase: DadosVeiculoAddUseCase,
) : DadosVeiculoInteractor {
    override fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) = dadosVeiculoAddUseCase.invoke(dadosVeiculo)
}