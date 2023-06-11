package com.example.custofrete.domain.useCase.usuario

import com.example.custofrete.domain.repository.UsuarioRepository

class UsuarioVerificarUsuarioLogadoUseCase(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke() = usuarioRepository.verificarUsuarioLogado()
}