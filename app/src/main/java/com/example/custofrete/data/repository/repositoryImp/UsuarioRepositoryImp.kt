package com.example.custofrete.data.repository.repositoryImp

import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSource
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow

class UsuarioRepositoryImp(
    private val usuarioDataSource: UsuarioDataSource
) : UsuarioRepository {

    override fun addUsuario(usuario: Usuario): Flow<Usuario> = usuarioDataSource.addUsuario(usuario)

    override fun verificarUsuarioLogado() = usuarioDataSource.verificarUserLogado()

}