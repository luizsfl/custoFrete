package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota

class EntregaRotaInteractorImp(
    private val entregaRotaAddUseCase: EntregaRotaAddUseCase,
    private val entregaRotaGetAllUseCase: EntregaRotaGetAllUseCase,
    private val entregaRotaDeleteUseCase: EntregaRotaDeleteUseCase,
    private val entregaRotaUpdateUseCase: EntregaRotaUpdateUseCase

) : EntregaRotaInteractor {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaAddUseCase.invoke(entrega)
    override fun getAllEntregaRota() = entregaRotaGetAllUseCase.invoke()
    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDeleteUseCase.invoke(entrega)
    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) = entregaRotaUpdateUseCase.invoke(idDocument,listaRotas,tipoTela)
}