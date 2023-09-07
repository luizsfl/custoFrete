package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.model.Entrega
import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaAddUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(entrega: Entrega) =
        entregaRotaRepository.addEntregaRota(entrega)
}