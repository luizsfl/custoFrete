package com.programacustofrete.custofrete.data.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AppApplication)
            modules(com.programacustofrete.custofrete.data.di.daoModule)
            modules(com.programacustofrete.custofrete.data.di.dataSourceModule)
            modules(com.programacustofrete.custofrete.data.di.repositoryModule)
            modules(com.programacustofrete.custofrete.data.di.useCaseModule)
            modules(com.programacustofrete.custofrete.data.di.interactorModule)
            modules(com.programacustofrete.custofrete.data.di.viewModel)

        }
    }
}