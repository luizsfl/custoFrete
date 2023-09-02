package com.example.custofrete.presentation.calculoRota

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaInteractor
import com.example.custofrete.presentation.ViewStateCustoCalculado
import com.example.custofrete.presentation.ViewStateEntregaRota
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class CalculoRotaViewModel (
    private val entregaRotaInteractor: EntregaRotaInteractor
): ViewModel(){

    private var _viewStateCustoRotaCalculada = MutableLiveData<ViewStateCustoCalculado>()
    var viewStateCustoRotaCalculada: LiveData<ViewStateCustoCalculado> = _viewStateCustoRotaCalculada

    private var _viewStateEntregaRota = MutableLiveData<ViewStateEntregaRota>()
    var viewStateEntregaRota: LiveData<ViewStateEntregaRota> = _viewStateEntregaRota

    fun getDistanciaRotaCalculada(listRota: List<Rota>?) {
        _viewStateCustoRotaCalculada.postValue(ViewStateCustoCalculado.sucessoCustoCalculado(distanciaRotaCalculada(listRota)))
    }


    fun setLoadingDistanciaRotaCalculada(loading:Boolean) {
        _viewStateCustoRotaCalculada.postValue(ViewStateCustoCalculado.Loading(loading))
    }

    fun setErroDistanciaRotaCalculada(mensagem:String) {
        _viewStateCustoRotaCalculada.postValue(ViewStateCustoCalculado.Failure(mensagem))
    }

    private fun distanciaRotaCalculada(listRota: List<Rota>?):Double {
        var valorMetroSequencia = 0.0
        listRota?.forEach {
            valorMetroSequencia += it.valorDistance.value
        }

        val random = valorMetroSequencia / 1000

        return random
    }

    fun addEntregaRota(entrega: Entrega) {
        viewModelScope.launch {
            entregaRotaInteractor.addEntregaRota(entrega)
                .onStart { _viewStateEntregaRota.value = ViewStateEntregaRota.Loading(loading = true) }
                .catch {
                    _viewStateEntregaRota.value = ViewStateEntregaRota.Failure(messengerError = it.message.orEmpty())
                }
                .collect { _viewStateEntregaRota.value = ViewStateEntregaRota.sucesso(it)}
        }
    }

}