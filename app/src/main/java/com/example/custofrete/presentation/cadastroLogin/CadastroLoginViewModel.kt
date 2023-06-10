package com.example.custofrete.presentation.cadastroLogin

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

class CadastroLoginViewModel (
    private val usuarioInteractor: UsuarioInteractor,
): ViewModel(){

    private var _viewState = MutableLiveData<ViewState>()
    var viewState: LiveData<ViewState> = _viewState

    fun addUsuario(usuario: Usuario) {
        if(validUsuario(usuario)) {
            viewModelScope.launch {
                usuarioInteractor.addUsuario(usuario)
                    .onStart { _viewState.value = ViewState.Loading(loading = true) }
                    .catch {
                        _viewState.value = ViewState.Failure(messengerError = it.message.orEmpty())
                    }
                    .collect { setUsuario(it) }
            }
        }
    }


    private fun setUsuario(usuario: Usuario) {
        _viewState.value = ViewState.sucessoUsuario(usuario)
    }

    private fun validUsuario(usuario: Usuario):Boolean{

        var lUsuarioValido: Boolean

        if (!usuario.nome.isEmpty()){
            if (!usuario.email.isEmpty()){
                if (!usuario.senha.isEmpty()){
                    //usuario esta totalmente valido
                    lUsuarioValido = true
                }else{
                    _viewState.value =  ViewState.Failure(messengerError = "Preencha a senha")
                    lUsuarioValido = false
                }
            }else{
                _viewState.value =  ViewState.Failure(messengerError = "Preencha o E-mail")
                lUsuarioValido = false
            }
        }else{
            _viewState.value =  ViewState.Failure(messengerError = "Preencha o nome")
            lUsuarioValido = false
        }
        return lUsuarioValido
    }
}