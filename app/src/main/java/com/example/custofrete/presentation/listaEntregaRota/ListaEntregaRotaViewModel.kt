package com.example.custofrete.presentation.listaEntregaRota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaInteractor
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoInteractor
import com.example.custofrete.presentation.ViewStateDadosVeiculo
import com.example.custofrete.presentation.ViewStateEntregaRota
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListaEntregaRotaViewModel (
    private val entregaRotaInteractor: EntregaRotaInteractor
): ViewModel(){

    private var _viewStateListEntregaRota = MutableLiveData<ViewStateEntregaRota>()
    var viewStateListEntregaRota: LiveData<ViewStateEntregaRota> = _viewStateListEntregaRota


    fun getAllEntregaRota() {
        viewModelScope.launch {
            entregaRotaInteractor.getAllEntregaRota()
                .onStart { _viewStateListEntregaRota.value = ViewStateEntregaRota.Loading(loading = true) }
                .catch {
                    _viewStateListEntregaRota.value = ViewStateEntregaRota.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateListEntregaRota.value = ViewStateEntregaRota.sucessoGetAll(it)}
        }
    }

    fun deleteEntregaRota(entrega: Entrega) {
        viewModelScope.launch {
            entregaRotaInteractor.deleteEntregaRota(entrega)
                .onStart { _viewStateListEntregaRota.value = ViewStateEntregaRota.Loading(loading = true) }
                .catch {
                    _viewStateListEntregaRota.value = ViewStateEntregaRota.Failure(messengerError = it.message.orEmpty())
                }
                .collect {

                    getAllEntregaRota()

                }
        }
    }
}