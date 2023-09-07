package com.programacustofrete.custofrete.data.di

import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.DadosVeiculoDao
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.EntregaSimplesDao
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSource
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSourceImp
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSourceImp
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSource
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.entregaSimples.EntregaSimplesDataSourceImp
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSource
import com.programacustofrete.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSourceImp
import com.programacustofrete.custofrete.data.repository.repositoryImp.DadosVeiculoRepositoryImp
import com.programacustofrete.custofrete.data.repository.repositoryImp.EntregaRotaRepositoryImp
import com.programacustofrete.custofrete.data.repository.repositoryImp.EntregaSimplesRepositoryImp
import com.programacustofrete.custofrete.data.repository.repositoryImp.UsuarioRepositoryImp
import com.programacustofrete.custofrete.domain.repository.DadosVeiculoRepository
import com.programacustofrete.custofrete.domain.repository.EntregaRotaRepository
import com.programacustofrete.custofrete.domain.repository.EntregaSimplesRepository
import com.programacustofrete.custofrete.domain.repository.UsuarioRepository
import com.programacustofrete.custofrete.domain.useCase.entregaRota.*
import com.programacustofrete.custofrete.domain.useCase.entregaSimples.*
import com.programacustofrete.custofrete.domain.useCase.usuario.UsuarioAddUseCase
import com.programacustofrete.custofrete.domain.useCase.usuario.UsuarioInteractor
import com.programacustofrete.custofrete.domain.useCase.usuario.UsuarioInteractorImp
import com.programacustofrete.custofrete.domain.useCase.usuario.UsuarioLogadoUseCase
import com.programacustofrete.custofrete.domain.useCase.veiculo.DadosVeiculoAddUseCase
import com.programacustofrete.custofrete.domain.useCase.veiculo.DadosVeiculoGetUseCase
import com.programacustofrete.custofrete.domain.useCase.veiculo.DadosVeiculoInteractor
import com.programacustofrete.custofrete.domain.useCase.veiculo.DadosVeiculoInteractorImp
import com.programacustofrete.custofrete.presentation.cadastroLogin.CadastroLoginViewModel
import com.programacustofrete.custofrete.presentation.calculoRota.CalculoRotaViewModel
import com.programacustofrete.custofrete.presentation.calculoSimples.CalculoSimplesViewModel
import com.programacustofrete.custofrete.presentation.dadosEntregaRota.DadosEntregaRotaViewModel
import com.programacustofrete.custofrete.presentation.dadosVeiculo.DadosVeiculoViewModel
import com.programacustofrete.custofrete.presentation.listaEntregaRota.ListaEntregaRotaViewModel
import com.programacustofrete.custofrete.presentation.listaEntregaSimples.ListaEntregaSimplesViewModel
import com.programacustofrete.custofrete.presentation.login.LoginViewModel
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