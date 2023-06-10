package com.example.custofrete.data.di

import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.data.repository.remoteDataSource.dataSource.UsuarioDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.UsuarioDataSourceImp
import com.example.custofrete.data.repository.repositoryImp.UsuarioRepositoryImp
import com.example.custofrete.domain.repository.UsuarioRepository
import com.example.custofrete.domain.useCase.usuario.UsuarioAddUseCase
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractorImp
import com.example.custofrete.presentation.cadastroLogin.CadastroLoginViewModel
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
}

val interactorModule = module {
    factory<UsuarioInteractor> {UsuarioInteractorImp(usuarioAddUseCase = get()) }
}

val viewModel = module {
    viewModel {
        CadastroLoginViewModel(usuarioInteractor = get())
    }
}