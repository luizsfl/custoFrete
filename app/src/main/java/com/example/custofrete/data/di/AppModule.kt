package com.example.custofrete.data.di

import com.example.custofrete.data.repository.remoteDataSource.dao.DadosVeiculoDao
import com.example.custofrete.data.repository.remoteDataSource.dao.EntregaRotaDao
import com.example.custofrete.data.repository.remoteDataSource.dao.UsuarioDao
import com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.dadosVeiculo.DadosVeiculoDataSourceImp
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.entregaRota.EntregaRotaDataSourceImp
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSource
import com.example.custofrete.data.repository.remoteDataSource.dataSource.usuario.UsuarioDataSourceImp
import com.example.custofrete.data.repository.repositoryImp.DadosVeiculoRepositoryImp
import com.example.custofrete.data.repository.repositoryImp.EntregaRotaRepositoryImp
import com.example.custofrete.data.repository.repositoryImp.UsuarioRepositoryImp
import com.example.custofrete.domain.repository.DadosVeiculoRepository
import com.example.custofrete.domain.repository.EntregaRotaRepository
import com.example.custofrete.domain.repository.UsuarioRepository
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaAddUseCase
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaInteractor
import com.example.custofrete.domain.useCase.entregaRota.EntregaRotaInteractorImp
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
import com.example.custofrete.presentation.dadosVeiculo.DadosVeiculoViewModel
import com.example.custofrete.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val daoModule = module {
    factory { UsuarioDao() }
    factory { DadosVeiculoDao() }
    factory { EntregaRotaDao() }

}

val dataSourceModule = module {
    factory<UsuarioDataSource> { UsuarioDataSourceImp(usuarioDao = get()) }
    factory<DadosVeiculoDataSource> { DadosVeiculoDataSourceImp(dadosVeiculoDao = get()) }
    factory<EntregaRotaDataSource> { EntregaRotaDataSourceImp(entregaRotaDao = get()) }

}

val repositoryModule = module {
    factory<UsuarioRepository> { UsuarioRepositoryImp(usuarioDataSource = get()) }
    factory<DadosVeiculoRepository> { DadosVeiculoRepositoryImp(dadosVeiculoDataSource = get()) }
    factory<EntregaRotaRepository> { EntregaRotaRepositoryImp(entregaRotaDataSource = get()) }

}

val useCaseModule = module {
    factory { UsuarioAddUseCase(usuarioRepository = get()) }
    factory { UsuarioLogadoUseCase(usuarioRepository = get()) }
    factory { DadosVeiculoAddUseCase(dadosVeiculoRepository = get()) }
    factory { DadosVeiculoGetUseCase(dadosVeiculoRepository = get()) }
    factory { EntregaRotaAddUseCase(entregaRotaRepository = get()) }


}

val interactorModule = module {
    factory<UsuarioInteractor> {UsuarioInteractorImp(usuarioAddUseCase = get(),usuarioVerificarUsuarioLogadoUseCase=get()) }
    factory<DadosVeiculoInteractor> {DadosVeiculoInteractorImp(dadosVeiculoAddUseCase = get(), dadosVeiculoGetUseCase = get()) }
    factory<EntregaRotaInteractor> {EntregaRotaInteractorImp(entregaRotaAddUseCase = get()) }

}

val viewModel = module {

    viewModel {
        CadastroLoginViewModel(usuarioInteractor = get())
    }

    viewModel {
        LoginViewModel( usuarioInteractor = get())
    }

    viewModel {
        DadosVeiculoViewModel( dadosVeiculoInteractor = get())
    }

    viewModel {
        CalculoRotaViewModel(entregaRotaInteractor = get())
    }

}