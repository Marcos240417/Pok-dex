package com.example.newpokedex.pokemon_list_feature.presentation.state

import androidx.paging.PagingData
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.util.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PokemonListState(
    val pokemons: Flow<PagingData<Pokemon>> = emptyFlow(),
    val searchQuery: String = "",
    val searchResult: ResultData<List<Pokemon>>? = null
)