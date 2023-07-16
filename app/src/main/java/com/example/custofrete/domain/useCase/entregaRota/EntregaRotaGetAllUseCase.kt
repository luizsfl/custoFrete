package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaGetAllUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke() = entregaRotaRepository.getAllEntregaRota()
}