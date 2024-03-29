package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.model.Entrega
import com.programacustofrete.custofrete.domain.model.Rota

class EntregaRotaInteractorImp(
    private val entregaRotaAddUseCase: EntregaRotaAddUseCase,
    private val entregaRotaGetAllUseCase: EntregaRotaGetAllUseCase,
    private val entregaRotaDeleteUseCase: EntregaRotaDeleteUseCase,
    private val entregaRotaUpdateUseCase: EntregaRotaUpdateUseCase,
    private val entregaRotaUpdateStatusUseCase: EntregaRotaUpdateStatusUseCase
) : EntregaRotaInteractor {
    override fun addEntregaRota(entrega: Entrega) = entregaRotaAddUseCase.invoke(entrega)
    override fun getAllEntregaRota() = entregaRotaGetAllUseCase.invoke()
    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDeleteUseCase.invoke(entrega)
    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) = entregaRotaUpdateUseCase.invoke(idDocument,listaRotas,tipoTela)
    override fun entregaRotaUpdateStatusUseCase(idDocument: String, status: String)=entregaRotaUpdateStatusUseCase.invoke(idDocument,status)
}