package com.programacustofrete.custofrete.domain.useCase.usuario

import com.programacustofrete.custofrete.domain.model.Usuario
import com.programacustofrete.custofrete.domain.repository.UsuarioRepository

class UsuarioAddUseCase(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke(usuario: Usuario) =
        usuarioRepository.addUsuario(usuario)
}