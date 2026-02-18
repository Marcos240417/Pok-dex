package com.example.newpokedex.app.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@PokedexApplication)

            // Aqui você carrega todas as suas variáveis de módulo separadamente
            modules(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}