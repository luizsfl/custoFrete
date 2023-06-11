package com.example.custofrete.domain.useCase.usuario

import com.example.custofrete.domain.model.Usuario

class UsuarioInteractorImp(
    private val usuarioAddUseCase: UsuarioAddUseCase,
    private val usuarioVerificarUsuarioLogadoUseCase: UsuarioVerificarUsuarioLogadoUseCase
) : UsuarioInteractor {
    override fun addUsuario(usuario: Usuario) = usuarioAddUseCase.invoke(usuario)
    override fun verificarUsuarioLogado() = usuarioVerificarUsuarioLogadoUseCase.invoke()
}