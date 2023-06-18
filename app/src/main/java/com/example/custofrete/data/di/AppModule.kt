package com.example.custofrete.data.di

import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSourceImp
import com.example.custofrete.data.repository.repositoryImp.UsuarioRepositoryImp
import com.example.custofrete.domain.repository.UsuarioRepository
import com.example.custofrete.domain.useCase.usuario.UsuarioAddUseCase
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractorImp
import com.example.custofrete.domain.useCase.usuario.UsuarioLogadoUseCase
import com.example.custofrete.presentation.cadastroLogin.CadastroLoginViewModel
import com.example.custofrete.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val daoModule = module {
    factory { UsuarioDao() }
}

val dataSourceModule = module {
    factory<UsuarioDataSource> { UsuarioDataSourceImp(usuarioDao = get()) }
}

val repositoryModule = module {
    factory<UsuarioRepository> { UsuarioRepositoryImp(usuarioDataSource = get()) }
}

val useCaseModule = module {
    factory { UsuarioAddUseCase(usuarioRepository = get()) }
    factory { UsuarioLogadoUseCase(usuarioRepository = get()) }
}

val interactorModule = module {
    factory<UsuarioInteractor> {UsuarioInteractorImp(usuarioAddUseCase = get(),usuarioVerificarUsuarioLogadoUseCase=get()) }
}

val viewModel = module {

    viewModel {
        CadastroLoginViewModel(usuarioInteractor = get())
    }

    viewModel {
        LoginViewModel( usuarioInteractor = get())
    }

}