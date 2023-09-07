package com.programacustofrete.custofrete.domain.useCase.entregaSimples

import com.programacustofrete.custofrete.domain.model.EntregaSimples
import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository

class EntregaSimplesDeleteUseCase(
    private val entregaSimplesRepository: EntregaSimplesRepository
) {
    operator fun invoke(entrega: EntregaSimples) =
        entregaSimplesRepository.deleteEntregaSimples(entrega)
}