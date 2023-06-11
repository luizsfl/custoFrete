package com.example.custofrete.data.repository.remoteDataSource.dataSource

import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.repository.UsuarioRepository
import com.example.custofrete.presentation.ViewState
import kotlinx.coroutines.flow.Flow

class UsuarioDataSourceImp(
    private val usuarioDao: UsuarioDao
) :UsuarioDataSource{
    override fun addUsuario(usuario: Usuario) = usuarioDao.addUsuario(usuario)
    override fun verificarUserLogado() = usuarioDao.verificarUserLogado()
}