package com.example.custofrete.domain.useCase.entregaRota

import com.example.custofrete.domain.model.Rota
import com.example.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaUpdateUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) = entregaRotaRepository.updateEntregaRota(idDocument,listaRotas,tipoTela)

}