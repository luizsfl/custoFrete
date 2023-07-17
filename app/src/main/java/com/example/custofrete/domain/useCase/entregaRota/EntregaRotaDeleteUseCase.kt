package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaDeleteUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(entrega:Entrega) = entregaRotaRepository.deleteEntregaRotaDao(entrega)
}