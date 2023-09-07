package com.programacustofrete.custofrete.presentation.calculoSimples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacustofrete.custofrete.domain.model.EntregaSimples
import com.programacustofrete.custofrete.domain.useCase.entregaSimples.EntregaSimplesInteractor
import com.programacustofrete.custofrete.presentation.ViewStateEntregaSimples
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class CalculoSimplesViewModel(
    private val entregaSimplesInteractor: EntregaSimplesInteractor
) : ViewModel() {

    private var _viewStateEntregaSimples = MutableLiveData<ViewStateEntregaSimples>()
    var viewStateEntregaSimples: LiveData<ViewStateEntregaSimples> = _viewStateEntregaSimples

    fun addEntregaRota(entrega: EntregaSimples) {
        viewModelScope.launch {
            entregaSimplesInteractor.addEntregaSimples(entrega)
                .onStart {
                    _viewStateEntregaSimples.value = ViewStateEntregaSimples.Loading(loading = true)
                }
                .catch {
                    _viewStateEntregaSimples.value =
                        ViewStateEntregaSimples.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateEntregaSimples.value = ViewStateEntregaSimples.sucesso(it) }
        }
    }



    fun updateEntregaRota(entrega: EntregaSimples) {
        viewModelScope.launch {
            entregaSimplesInteractor.updateEntregaSimples(entrega)
                .onStart {
                    _viewStateEntregaSimples.value = ViewStateEntregaSimples.Loading(loading = true)
                }
                .catch {
                    _viewStateEntregaSimples.value =
                        ViewStateEntregaSimples.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateEntregaSimples.value = ViewStateEntregaSimples.sucesso(it) }
        }
    }

}