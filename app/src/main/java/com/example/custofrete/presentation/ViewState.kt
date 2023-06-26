package com.example.custofrete.presentation

import com.example.custofrete.domain.model.DadosVeiculo
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