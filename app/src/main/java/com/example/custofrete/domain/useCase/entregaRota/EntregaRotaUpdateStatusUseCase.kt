package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaUpdateStatusUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(idDocument: String,status:String) = entregaRotaRepository.updateEntregaRotaStatus(idDocument,status)
}