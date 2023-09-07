package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.usuario

import com.programacustofrete.custofrete.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioDataSource {
    fun addUsuario(usuario: Usuario): Flow<Usuario>
    fun verificarUserLogado(): Flow<Boolean>

}