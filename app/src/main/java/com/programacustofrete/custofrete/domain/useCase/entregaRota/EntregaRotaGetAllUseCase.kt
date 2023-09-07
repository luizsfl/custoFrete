package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaGetAllUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke() = entregaRotaRepository.getAllEntregaRota()
}