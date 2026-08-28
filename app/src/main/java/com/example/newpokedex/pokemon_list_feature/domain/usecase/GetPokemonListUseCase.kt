package com.example.newpokedex.pokemon_list_feature.domain.usecase

import androidx.paging.PagingData
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.pokemon_list_feature.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(
    private val repository: PokemonRepository
) {
    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return repository.getPokemonList()
    }
}