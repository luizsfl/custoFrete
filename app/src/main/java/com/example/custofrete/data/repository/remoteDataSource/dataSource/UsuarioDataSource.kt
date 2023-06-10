package com.example.custofrete.data.repository.remoteDataSource.dataSource

import com.example.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioDataSource {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
}