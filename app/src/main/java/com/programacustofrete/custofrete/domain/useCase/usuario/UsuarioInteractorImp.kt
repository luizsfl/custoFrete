package com.programacustofrete.custofrete.domain.useCase.usuario

import com.programacustofrete.custofrete.domain.model.Usuario

class UsuarioInteractorImp(
    private val usuarioAddUseCase: UsuarioAddUseCase,
    private val usuarioVerificarUsuarioLogadoUseCase: UsuarioLogadoUseCase
) : UsuarioInteractor {
    override fun addUsuario(usuario: Usuario) = usuarioAddUseCase.invoke(usuario)
    override fun verificarUsuarioLogado() = usuarioVerificarUsuarioLogadoUseCase.invoke()
}