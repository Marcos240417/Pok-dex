package com.example.newpokedex.app.di

import com.example.newpokedex.core.data.db.PokemonDatabase
import com.example.newpokedex.core.domain.repository.PokemonRepository
import com.example.newpokedex.core.domain.repository.PokemonRepositoryImpl
import com.example.newpokedex.core.remote.PokeApiService
import com.example.newpokedex.feature.PokemonViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    // Cria a instância do Banco de Dados
    single {
        PokemonDatabase.getDatabase(androidContext())
    }


    // Cria o DAO a partir da instância do banco acima
    single { get<PokemonDatabase>().pokemonDao() }
}

val repositoryModule = module {
    // Injeta automaticamente a API (do networkModule) e o DAO (do databaseModule)
    single<PokemonRepository> {
        PokemonRepositoryImpl(api = get(), dao = get())
    }
}

val viewModelModule = module {
    // O Koin injeta o 'repository' que foi definido no 'repositoryModule'
    viewModel { PokemonViewModel(repository = get()) }
}


    // 3. Provedor do Retrofit e da PokeApiService
val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
        }
    }

