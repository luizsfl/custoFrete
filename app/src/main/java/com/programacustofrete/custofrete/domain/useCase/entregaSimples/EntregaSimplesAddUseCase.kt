package com.programacustofrete.custofrete.domain.useCase.entregaSimples

import com.programacustofrete.custofrete.domain.model.EntregaSimples
import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesAddUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke(entrega: EntregaSimples) =
        entregaSimplesRepository.addEntregaSimples(entrega)
}