package com.example.newpokedex.core.di

import com.example.newpokedex.core.data.local.db.PokemonDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDatabaseModule = module {
    single { PokemonDatabase.getDatabase(androidContext()) }
    single { get<PokemonDatabase>().pokemonDao() }
}