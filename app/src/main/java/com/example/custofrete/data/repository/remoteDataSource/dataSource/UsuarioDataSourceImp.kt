package com.example.custofrete.data.repository.remoteDataSource.dataSource

import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.repository.UsuarioRepository

class UsuarioDataSourceImp(
    private val usuarioDataSource: UsuarioDataSource
) :UsuarioRepository{
    override fun addUsuario(usuario: Usuario) = usuarioDataSource.addUsuario(usuario)
}