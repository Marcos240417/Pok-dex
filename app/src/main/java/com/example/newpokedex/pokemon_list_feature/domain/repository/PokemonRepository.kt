package com.example.newpokedex.pokemon_list_feature.domain.repository

import androidx.paging.PagingData
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.util.ResultData
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<PagingData<Pokemon>>
    fun searchPokemon(query: String): Flow<ResultData<List<Pokemon>>>
}