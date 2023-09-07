package com.programacustofrete.custofrete.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programacustofrete.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.programacustofrete.custofrete.presentation.ViewStateUsuario
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginViewModel (
    private val usuarioInteractor: UsuarioInteractor,
): ViewModel(){

    private var _viewStateUsuario = MutableLiveData<ViewStateUsuario>()
    var viewStateUsuario: LiveData<ViewStateUsuario> = _viewStateUsuario

    fun VerificarUserLogado() {
            viewModelScope.launch {
                usuarioInteractor.verificarUsuarioLogado()
                    .onStart { _viewStateUsuario.value = ViewStateUsuario.Loading(loading = true) }
                    .catch {
                        _viewStateUsuario.value = ViewStateUsuario.Failure(messengerError = it.message.orEmpty())
                    }
                    .collect { setLogado(it) }

        }
    }


    private fun setLogado(usuarioLogado:Boolean) {
        _viewStateUsuario.value = ViewStateUsuario.Logado(usuarioLogado)
    }


}