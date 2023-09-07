package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaUpdateStatusUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(idDocument: String,status:String) = entregaRotaRepository.updateEntregaRotaStatus(idDocument,status)
}