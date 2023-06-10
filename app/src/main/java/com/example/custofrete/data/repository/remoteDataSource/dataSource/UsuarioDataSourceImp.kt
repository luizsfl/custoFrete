package com.example.custofrete.data.repository.remoteDataSource.dataSource

import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.repository.UsuarioRepository

class UsuarioDataSourceImp(
    private val usuarioDao: UsuarioDao
) :UsuarioDataSource{
    override fun addUsuario(usuario: Usuario) = usuarioDao.addUsuario(usuario)
}