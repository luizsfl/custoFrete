package com.example.custofrete.presentation.cadastroLogin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.example.custofrete.presentation.ViewStateUsuario
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CadastroLoginViewModel (
    private val usuarioInteractor: UsuarioInteractor,
): ViewModel(){

    private var _viewStateUsuario = MutableLiveData<ViewStateUsuario>()
    var viewStateUsuario: LiveData<ViewStateUsuario> = _viewStateUsuario

    fun addUsuario(usuario: Usuario) {
        if(validUsuario(usuario)) {
            viewModelScope.launch {
                usuarioInteractor.addUsuario(usuario)
                    .onStart { _viewStateUsuario.value = ViewStateUsuario.Loading(loading = true) }
                    .catch {
                        _viewStateUsuario.value = ViewStateUsuario.Failure(messengerError = it.message.orEmpty())
                    }
                    .collect { setUsuario(it) }
            }
        }
    }


    private fun setUsuario(usuario: Usuario) {
        _viewStateUsuario.value = ViewStateUsuario.sucessoUsuario(usuario)
    }

    private fun validUsuario(usuario: Usuario):Boolean{

        var lUsuarioValido: Boolean

        if (!usuario.nome.isEmpty()){
            if (!usuario.email.isEmpty()){
                if (!usuario.senha.isEmpty()){
                    //usuario esta totalmente valido
                    lUsuarioValido = true
                }else{
                    _viewStateUsuario.value =  ViewStateUsuario.Failure(messengerError = "Preencha a senha")
                    lUsuarioValido = false
                }
            }else{
                _viewStateUsuario.value =  ViewStateUsuario.Failure(messengerError = "Preencha o E-mail")
                lUsuarioValido = false
            }
        }else{
            _viewStateUsuario.value =  ViewStateUsuario.Failure(messengerError = "Preencha o nome")
            lUsuarioValido = false
        }
        return lUsuarioValido
    }
}