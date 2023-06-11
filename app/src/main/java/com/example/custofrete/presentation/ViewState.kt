package com.example.custofrete.presentation

import com.example.custofrete.domain.model.Usuario

sealed class ViewState {
    data class Loading(val loading: Boolean):ViewState()
    data class sucessoUsuario(val usuario: Usuario):ViewState()
    data class Failure(val messengerError:String = String()): ViewState()
    data class Logado(val usuarioLogado:Boolean = false): ViewState()

}