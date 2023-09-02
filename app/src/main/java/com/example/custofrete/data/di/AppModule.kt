package com.example.custofrete.data.di

import com.example.custofrete.data.repository.remoteDataSource.dao.DadosVeiculoDao
import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaSimplesDao
import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSourceImp
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSourceImp
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSourceImp
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSourceImp
import com.example.custofrete.data.repository.repositoryImp.DadosVeiculoRepositoryImp
import com.example.custofrete.data.repository.repositoryImp.EntregaRotaRepositoryImp
import com.example.custofrete.data.repository.repositoryImp.EntregaSimplesRepositoryImp
import com.example.custofrete.data.repository.repositoryImp.UsuarioRepositoryImp
import com.example.custofrete.domain.repository.DadosVeiculoRepository
import com.example.custofrete.domain.repository.EntregaRotaRepository
import com.example.custofrete.domain.repository.EntregaSimplesRepository
import com.example.custofrete.domain.repository.UsuarioRepository
import com.example.custofrete.domain.useCase.entregaRota.*
import com.example.custofrete.domain.useCase.entregaSimples.*
import com.example.custofrete.domain.useCase.usuario.UsuarioAddUseCase
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.example.custofrete.domain.useCase.usuario.UsuarioInteractorImp
import com.example.custofrete.domain.useCase.usuario.UsuarioLogadoUseCase
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoAddUseCase
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoGetUseCase
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoInteractor
import com.example.custofrete.domain.useCase.veiculo.DadosVeiculoInteractorImp
import com.example.custofrete.presentation.cadastroLogin.CadastroLoginViewModel
import com.example.custofrete.presentation.calculoRota.CalculoRotaViewModel
import com.example.custofrete.presentation.calculoSimples.CalculoSimplesViewModel
import com.example.custofrete.presentation.dadosEntregaRota.DadosEntregaRotaViewModel
import com.example.custofrete.presentation.dadosVeiculo.DadosVeiculoViewModel
import com.example.custofrete.presentation.listaEntregaRota.ListaEntregaRotaViewModel
import com.example.custofrete.presentation.listaEntregaSimples.ListaEntregaSimplesViewModel
import com.example.custofrete.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val daoModule = module {
    factory { UsuarioDao() }
    factory { DadosVeiculoDao() }
    factory { EntregaRotaDao() }
    factory { EntregaSimplesDao() }
}

val dataSourceModule = module {
    factory<UsuarioDataSource> { UsuarioDataSourceImp(usuarioDao = get()) }
    factory<DadosVeiculoDataSource> { DadosVeiculoDataSourceImp(dadosVeiculoDao = get()) }
    factory<EntregaRotaDataSource> { EntregaRotaDataSourceImp(entregaRotaDao = get()) }
    factory<EntregaSimplesDataSource> { EntregaSimplesDataSourceImp(entregaSimplesDao = get()) }
}

val repositoryModule = module {
    factory<UsuarioRepository> { UsuarioRepositoryImp(usuarioDataSource = get()) }
    factory<DadosVeiculoRepository> { DadosVeiculoRepositoryImp(dadosVeiculoDataSource = get()) }
    factory<EntregaRotaRepository> { EntregaRotaRepositoryImp(entregaRotaDataSource = get()) }
    factory<EntregaSimplesRepository> { EntregaSimplesRepositoryImp(entregaSimplesDataSource = get()) }
}

val useCaseModule = module {
    factory { UsuarioAddUseCase(usuarioRepository = get()) }
    factory { UsuarioLogadoUseCase(usuarioRepository = get()) }
    factory { DadosVeiculoAddUseCase(dadosVeiculoRepository = get()) }
    factory { DadosVeiculoGetUseCase(dadosVeiculoRepository = get()) }
    factory { EntregaRotaAddUseCase(entregaRotaRepository = get()) }
    factory { EntregaRotaGetAllUseCase(entregaRotaRepository = get()) }
    factory { EntregaRotaDeleteUseCase(entregaRotaRepository = get()) }
    factory { EntregaRotaUpdateUseCase(entregaRotaRepository = get()) }
    factory { EntregaSimplesAddUseCase(entregaSimplesRepository = get()) }
    factory { EntregaSimplesGetAllUseCase(entregaSimplesRepository = get()) }
    factory { EntregaSimplesDeleteUseCase(entregaSimplesRepository = get()) }
    factory { EntregaSimplesUpdateUseCase(entregaSimplesRepository = get()) }
    factory { EntregaRotaUpdateStatusUseCase(entregaRotaRepository = get()) }
}

val interactorModule = module {
    factory<UsuarioInteractor> {UsuarioInteractorImp(usuarioAddUseCase = get(),usuarioVerificarUsuarioLogadoUseCase=get()) }
    factory<DadosVeiculoInteractor> {DadosVeiculoInteractorImp(dadosVeiculoAddUseCase = get(), dadosVeiculoGetUseCase = get()) }
    factory<EntregaRotaInteractor> {EntregaRotaInteractorImp(
        entregaRotaAddUseCase = get(),
        entregaRotaGetAllUseCase = get(),
        entregaRotaDeleteUseCase = get(),
        entregaRotaUpdateUseCase = get(),
        entregaRotaUpdateStatusUseCase = get()
    ) }
    factory<EntregaSimplesInteractor> {
        EntregaSimplesInteractorImp(
        entregaSimplesAddUseCase = get(),
        entregaSimplesGetAllUseCase = get(),
        entregaSimplesDeleteUseCase = get(),
        entregaSimplesUpdateUseCase = get()
    ) }

}

val viewModel = module {

    viewModel {CadastroLoginViewModel(usuarioInteractor = get()) }
    viewModel {LoginViewModel( usuarioInteractor = get()) }
    viewModel {DadosVeiculoViewModel( dadosVeiculoInteractor = get())  }
    viewModel {CalculoRotaViewModel(entregaRotaInteractor = get())}
    viewModel {ListaEntregaRotaViewModel(entregaRotaInteractor = get()) }
    viewModel {DadosEntregaRotaViewModel(entregaRotaInteractor = get()) }
    viewModel {CalculoSimplesViewModel(entregaSimplesInteractor = get())}
    viewModel {ListaEntregaSimplesViewModel(entregaSimplesInteractor = get())}
}