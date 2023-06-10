package com.example.custofrete.domain.useCase.usuario

import com.example.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

class UsuarioInteractorImp(
    private val usuarioAddUseCase: UsuarioAddUseCase
) : UsuarioInteractor {
    override fun addUsuario(usuario: Usuario): Flow<Usuario> = usuarioAddUseCase.invoke(usuario)
}