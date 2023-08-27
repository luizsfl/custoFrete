package com.example.custofrete.domain.useCase.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesDeleteUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke(entrega: EntregaSimples) =
        entregaSimplesRepository.deleteEntregaSimples(entrega)
}