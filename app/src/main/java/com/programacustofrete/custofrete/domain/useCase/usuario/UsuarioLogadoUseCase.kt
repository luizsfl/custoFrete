package com.programacustofrete.custofrete.domain.useCase.usuario

import com.programacustofrete.custofrete.domain.repository.UsuarioRepository

class UsuarioLogadoUseCase(
    private val usuarioRepository: UsuarioRepository
) {
    operator fun invoke() = usuarioRepository.verificarUsuarioLogado()
}