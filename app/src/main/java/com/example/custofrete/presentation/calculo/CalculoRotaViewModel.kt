package com.example.custofrete.presentation.calculo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.custofrete.domain.model.Rota
import com.example.custofrete.presentation.ViewStateCustoCalculado


class CalculoRotaViewModel (
): ViewModel(){

    private var _viewStateCustoRotaCalculada = MutableLiveData<ViewStateCustoCalculado>()
    var viewStateCustoRotaCalculada: LiveData<ViewStateCustoCalculado> = _viewStateCustoRotaCalculada

    fun getDistanciaRotaCalculada(listRota: List<Rota>?) {
        _viewStateCustoRotaCalculada.postValue(ViewStateCustoCalculado.sucessoCustoCalculado(distanciaRotaCalculada(listRota)))
    }


    private fun distanciaRotaCalculada(listRota: List<Rota>?):Double {
        var valorMetroSequencia = 0.0
        listRota?.forEach {
            valorMetroSequencia += it.valorDistance.value
        }

        val random = valorMetroSequencia / 1000

        return random
    }

}