package com.example.custofrete.domain.useCase.usuario

import com.example.custofrete.domain.model.Usuario
import com.example.custofrete.domain.repository.UsuarioRepository

class UsuarioAddUseCase(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke(usuario: Usuario) =
        usuarioRepository.addUsuario(usuario)
}