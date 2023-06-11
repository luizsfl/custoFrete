package com.example.custofrete.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.example.custofrete.presentation.ViewState
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel (
    private val usuarioInteractor: UsuarioInteractor,
): ViewModel(){

    private var _viewState = MutableLiveData<ViewState>()
    var viewState: LiveData<ViewState> = _viewState

    fun VerificarUserLogado() {
            viewModelScope.launch {
                usuarioInteractor.verificarUsuarioLogado()
                    .onStart { _viewState.value = ViewState.Loading(loading = true) }
                    .catch {
                        _viewState.value = ViewState.Failure(messengerError = it.message.orEmpty())
                    }
                    .collect { setLogado(it) }

        }
    }


    private fun setLogado(usuarioLogado:Boolean) {
        _viewState.value = ViewState.Logado(usuarioLogado)
    }


}