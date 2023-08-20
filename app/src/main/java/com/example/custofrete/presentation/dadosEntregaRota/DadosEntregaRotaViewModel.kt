package com.example.custofrete.presentation.dadosEntregaRota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaInteractor
import com.example.custofrete.presentation.ViewStateRota
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DadosEntregaRotaViewModel (
    private val entregaRotaInteractor: EntregaRotaInteractor
): ViewModel(){

    private var _viewStateListEntregaRota = MutableLiveData<ViewStateRota>()
    var viewStateListEntregaRota: LiveData<ViewStateRota> = _viewStateListEntregaRota

    fun updateEntregaRota(idDocument: String, listaRotas: List<Rota>,tipoTela:Int) {
        viewModelScope.launch {
            entregaRotaInteractor.updateEntregaRota(idDocument,listaRotas,tipoTela)
                .onStart { _viewStateListEntregaRota.value = ViewStateRota.Loading(loading = true) }
                .catch {
                    _viewStateListEntregaRota.value = ViewStateRota.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateListEntregaRota.value = ViewStateRota.sucesso(it)}
        }
    }

}