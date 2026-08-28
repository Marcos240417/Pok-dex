package com.example.newpokedex.pokemon_list_feature.di

import com.example.newpokedex.pokemon_list_feature.data.repository.PokemonRepositoryImpl
import com.example.newpokedex.pokemon_list_feature.data.source.PokemonRemoteDataSourceImpl
import com.example.newpokedex.pokemon_list_feature.domain.repository.PokemonRepository
import com.example.newpokedex.pokemon_list_feature.domain.source.PokemonRemoteDataSource
import com.example.newpokedex.pokemon_list_feature.domain.usecase.GetPokemonListUseCase
import com.example.newpokedex.pokemon_list_feature.domain.usecase.SearchPokemonUseCase
import com.example.newpokedex.pokemon_list_feature.presentation.PokemonListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pokemonListModule = module {
    single<PokemonRemoteDataSource> {
        PokemonRemoteDataSourceImpl(
            api = get(),
            dao = get()
        )
    }

    single<PokemonRepository> {
        PokemonRepositoryImpl(
            remoteDataSource = get(),
            dao = get()
        )
    }

    factory { GetPokemonListUseCase(repository = get()) }

    factory { SearchPokemonUseCase(repository = get()) }

    viewModel {
        PokemonListViewModel(
            getPokemonListUseCase = get(),
            searchPokemonUseCase = get()
        )
    }
}