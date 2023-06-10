package com.example.custofrete.domain.repository

import com.example.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
}