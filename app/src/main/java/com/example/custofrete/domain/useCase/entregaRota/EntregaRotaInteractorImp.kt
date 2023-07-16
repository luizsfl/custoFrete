package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Entrega
import kotlinx.coroutines.flow.Flow

class EntregaRotaInteractorImp(
    private val entregaRotaAddUseCase: EntregaRotaAddUseCase
) : EntregaRotaInteractor {

    override fun addEntregaRota(entrega: Entrega): Flow<Entrega>  = entregaRotaAddUseCase.invoke(entrega)
}