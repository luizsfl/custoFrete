package com.example.custofrete.domain.useCase.entregaSimples

import com.example.custofrete.domain.model.EntregaSimples

class EntregaSimplesInteractorImp(
    private val entregaSimplesAddUseCase: EntregaSimplesAddUseCase,
    private val entregaSimplesGetAllUseCase: EntregaSimplesGetAllUseCase,
    private val entregaSimplesDeleteUseCase: EntregaSimplesDeleteUseCase,
    private val entregaSimplesUpdateUseCase: EntregaSimplesUpdateUseCase

) : EntregaSimplesInteractor {
    override fun addEntregaSimples(entrega: EntregaSimples) = entregaSimplesAddUseCase.invoke(entrega)
    override fun getAllEntregaSimples() = entregaSimplesGetAllUseCase.invoke()
    override fun deleteEntregaSimples(entrega: EntregaSimples) = entregaSimplesDeleteUseCase.invoke(entrega)
    override fun updateEntregaSimples(entrega: EntregaSimples) = entregaSimplesUpdateUseCase.invoke(entrega)
}