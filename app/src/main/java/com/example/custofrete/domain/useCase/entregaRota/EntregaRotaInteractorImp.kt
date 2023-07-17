package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Entrega

class EntregaRotaInteractorImp(
    private val entregaRotaAddUseCase: EntregaRotaAddUseCase,
    private val entregaRotaGetAllUseCase: EntregaRotaGetAllUseCase,
    private val entregaRotaDeleteUseCase: EntregaRotaDeleteUseCase

) : EntregaRotaInteractor {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaAddUseCase.invoke(entrega)
    override fun getAllEntregaRota() = entregaRotaGetAllUseCase.invoke()
    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDeleteUseCase.invoke(entrega)
}