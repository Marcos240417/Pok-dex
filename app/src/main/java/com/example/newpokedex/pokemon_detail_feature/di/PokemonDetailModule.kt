package com.example.newpokedex.pokemon_detail_feature.di

import com.example.newpokedex.pokemon_detail_feature.data.repository.PokemonDetailRepositoryImpl
import com.example.newpokedex.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import com.example.newpokedex.pokemon_detail_feature.domain.usecase.GetPokemonDetailUseCase
import com.example.newpokedex.pokemon_detail_feature.presentation.PokemonDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pokemonDetailModule = module {
    single<PokemonDetailRepository> { PokemonDetailRepositoryImpl(dao = get(), api = get()) }
    factory { GetPokemonDetailUseCase(repository = get()) }
    viewModel { PokemonDetailViewModel(getDetailUseCase = get()) }
}