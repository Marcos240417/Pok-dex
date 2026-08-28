package com.example.newpokedex.pokemon_list_feature.domain.source

import com.example.newpokedex.core.domain.model.Pokemon

interface PokemonRemoteDataSource {
    suspend fun getPokemons(limit: Int, offset: Int): List<Pokemon>
    suspend fun searchPokemon(query: String): Pokemon
}