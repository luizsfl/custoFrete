package com.example.custofrete.presentation.listaEntregaSimples

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.EntregaSimples
import com.example.custofrete.domain.useCase.entregaSimples.EntregaSimplesInteractor
import com.example.custofrete.presentation.ViewStateEntregaSimples
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListaEntregaSimplesViewModel (
    private val entregaSimplesInteractor: EntregaSimplesInteractor
): ViewModel(){

    private var _viewStateListEntregaSimples = MutableLiveData<ViewStateEntregaSimples>()
    var viewStateListEntregaSimples: LiveData<ViewStateEntregaSimples> = _viewStateListEntregaSimples


    fun getAllEntregaSimples() {
        viewModelScope.launch {
            entregaSimplesInteractor.getAllEntregaSimples()
                .onStart { _viewStateListEntregaSimples.value = ViewStateEntregaSimples.Loading(loading = true) }
                .catch {
                    _viewStateListEntregaSimples.value = ViewStateEntregaSimples.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateListEntregaSimples.value = ViewStateEntregaSimples.sucessoGetAll(it)}
        }
    }

    fun deleteEntregaSimples(entrega: EntregaSimples) {
        viewModelScope.launch {
            entregaSimplesInteractor.deleteEntregaSimples(entrega)
                .onStart { _viewStateListEntregaSimples.value = ViewStateEntregaSimples.Loading(loading = true) }
                .catch {
                    _viewStateListEntregaSimples.value = ViewStateEntregaSimples.Failure(messengerError = it.message.orEmpty())
                }
                .collect {

                    getAllEntregaSimples()

                }
        }
    }
}