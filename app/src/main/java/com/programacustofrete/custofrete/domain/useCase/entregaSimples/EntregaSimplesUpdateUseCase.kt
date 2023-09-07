package com.programacustofrete.custofrete.domain.useCase.entregaSimples

import com.programacustofrete.custofrete.domain.model.EntregaSimples
import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesUpdateUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke(entrega: EntregaSimples) =
        entregaSimplesRepository.updateEntregaSimples(entrega)
}