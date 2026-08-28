package com.example.newpokedex.pokemon_list_feature.domain.usecase

import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.util.ResultData
import com.example.newpokedex.pokemon_list_feature.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class SearchPokemonUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(query: String): Flow<ResultData<List<Pokemon>>> {
        return repository.searchPokemon(query)
    }
}