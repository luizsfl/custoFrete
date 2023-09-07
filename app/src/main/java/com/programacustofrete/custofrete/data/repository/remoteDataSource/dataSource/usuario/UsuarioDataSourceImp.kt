package com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.usuario

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.programacustofrete.custofrete.domain.model.Usuario

class UsuarioDataSourceImp(
    private val usuarioDao: UsuarioDao
) : UsuarioDataSource {
    override fun addUsuario(usuario: Usuario) = usuarioDao.addUsuario(usuario)
    override fun verificarUserLogado() = usuarioDao.verificarUserLogado()
}