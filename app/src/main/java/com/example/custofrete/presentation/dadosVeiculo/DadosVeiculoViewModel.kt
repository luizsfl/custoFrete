package com.example.custofrete.presentation.dadosVeiculo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoInteractor
import com.example.custofrete.presentation.ViewStateDadosVeiculo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DadosVeiculoViewModel (
    private val dadosVeiculoInteractor: DadosVeiculoInteractor,
): ViewModel(){

    private var _viewStateDadosVeiculo = MutableLiveData<ViewStateDadosVeiculo>()
    var viewStateDadosVeiculo: LiveData<ViewStateDadosVeiculo> = _viewStateDadosVeiculo

    fun resetViewState(){
        _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.Loading(loading = false)
    }

    fun addDadosVeiculo(dadosVeiculo: DadosVeiculo) {
        viewModelScope.launch {
            dadosVeiculoInteractor.addDadosVeiculo(dadosVeiculo)
                .onStart { _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.Loading(loading = true) }
                .catch {
                    _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.sucessoDadosVeiculo(it)}

        }
    }

    fun getDadosVeiculo() {
        viewModelScope.launch {
            dadosVeiculoInteractor.getDadosVeiculo()
                .onStart { _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.Loading(loading = true) }
                .catch {
                    _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateDadosVeiculo.value = ViewStateDadosVeiculo.getDadosVeiculo(it)}
        }
    }
}