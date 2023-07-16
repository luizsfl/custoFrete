package com.example.custofrete.presentation

import com.example.custofrete.domain.model.DadosVeiculo
import com.example.custofrete.domain.model.Entrega
import com.example.custofrete.domain.model.Usuario

sealed class ViewStateUsuario {
    data class Loading(val loading: Boolean):ViewStateUsuario()
    data class sucessoUsuario(val usuario: Usuario):ViewStateUsuario()
    data class Failure(val messengerError:String = String()): ViewStateUsuario()
    data class Logado(val usuarioLogado:Boolean = false): ViewStateUsuario()

}

sealed class ViewStateDadosVeiculo {
    data class Loading(val loading: Boolean):ViewStateDadosVeiculo()
    data class sucessoDadosVeiculo(val dadosVeiculo: DadosVeiculo):ViewStateDadosVeiculo()
    data class getDadosVeiculo(val dadosVeiculo: DadosVeiculo):ViewStateDadosVeiculo()
    data class Failure(val messengerError:String = String()): ViewStateDadosVeiculo()
}


sealed class ViewStateCustoCalculado {
    data class Loading(val loading: Boolean):ViewStateCustoCalculado()
    data class sucessoCustoCalculado(val custoCalculado: Double):ViewStateCustoCalculado()
    data class Failure(val messengerError:String = String()): ViewStateCustoCalculado()
}


sealed class ViewStateEntregaRota {
    data class Loading(val loading: Boolean):ViewStateEntregaRota()
    data class sucesso(val entrega:Entrega):ViewStateEntregaRota()
    data class sucessoGetAll(val listEntrega:List<Entrega>):ViewStateEntregaRota()
    data class Failure(val messengerError:String = String()): ViewStateEntregaRota()
}
