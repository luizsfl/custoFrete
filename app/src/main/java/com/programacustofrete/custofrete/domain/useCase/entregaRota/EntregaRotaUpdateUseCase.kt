package com.programacustofrete.custofrete.domain.useCase.entregaRota

import com.programacustofrete.custofrete.domain.model.Rota
import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository

class EntregaRotaUpdateUseCase(
    private val entregaRotaRepository: EntregaRotaRepository
) {
    operator fun invoke(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) = entregaRotaRepository.updateEntregaRota(idDocument,listaRotas,tipoTela)

}