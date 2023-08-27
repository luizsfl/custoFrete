package com.example.custofrete.domain.useCase.entregaSimples

import com.example.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesGetAllUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke() =
        entregaSimplesRepository.getAllEntregaSimples()
}