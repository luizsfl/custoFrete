package com.programacustofrete.custofrete.domain.useCase.usuario

import com.programacustofrete.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioInteractor {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
    fun verificarUsuarioLogado(): Flow<Boolean>

}