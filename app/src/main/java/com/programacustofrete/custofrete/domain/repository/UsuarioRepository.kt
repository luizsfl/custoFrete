package com.programacustofrete.custofrete.domain.repository

import com.programacustofrete.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
    fun verificarUsuarioLogado(): Flow<Boolean>
}