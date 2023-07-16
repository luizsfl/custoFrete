package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Entrega

class EntregaRotaInteractorImp(
    private val entregaRotaAddUseCase: EntregaRotaAddUseCase,
    private val entregaRotaGetAllUseCase: EntregaRotaGetAllUseCase

) : EntregaRotaInteractor {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaAddUseCase.invoke(entrega)
    override fun getAllEntregaRota() = entregaRotaGetAllUseCase.invoke()
}