package com.example.custofrete.domain.useCase.usuario

import com.example.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioInteractor {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
}