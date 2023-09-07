package com.programacustofrete.custofrete.domain.useCase.entregaSimples

import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesGetAllUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke() =
        entregaSimplesRepository.getAllEntregaSimples()
}