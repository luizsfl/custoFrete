package com.example.custofrete.domain.useCase.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples


class EntregaSimplesInteractorImp(
    private val entregaSimplesAddUseCase: EntregaSimplesAddUseCase
//    private val entregaRotaGetAllUseCase: EntregaRotaGetAllUseCase,
//    private val entregaRotaDeleteUseCase: EntregaRotaDeleteUseCase,
//    private val entregaRotaUpdateUseCase: EntregaRotaUpdateUseCase

) : EntregaSimplesInteractor {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesAddUseCase.invoke(entrega)

//    override fun getAllEntregaRota() = entregaRotaGetAllUseCase.invoke()
//    override fun deleteEntregaRota(entrega: Entrega) = entregaRotaDeleteUseCase.invoke(entrega)
//    override fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>, tipoTela:Int) = entregaRotaUpdateUseCase.invoke(idDocument,listaRotas,tipoTela)
}