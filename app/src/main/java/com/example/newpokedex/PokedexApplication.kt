package com.example.newpokedex

import android.app.Application
import com.example.newpokedex.core.di.coreDatabaseModule
import com.example.newpokedex.core.di.coreNetworkModule
import com.example.newpokedex.pokemon_detail_feature.di.pokemonDetailModule
import com.example.newpokedex.pokemon_list_feature.di.pokemonListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PokedexApplication)
            modules(
                coreNetworkModule,
                coreDatabaseModule,
                pokemonListModule,
                pokemonDetailModule
            )
        }
    }
}