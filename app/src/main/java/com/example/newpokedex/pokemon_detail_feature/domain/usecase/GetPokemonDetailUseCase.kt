package com.example.newpokedex.pokemon_detail_feature.domain.usecase

import com.example.newpokedex.core.data.local.PokemonWithDetails
import com.example.newpokedex.core.util.ResultData
import com.example.newpokedex.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokemonDetailUseCase(
    private val repository: PokemonDetailRepository
) {
    operator fun invoke(id: Int): Flow<ResultData<PokemonWithDetails>> = flow {
        emit(ResultData.Loading)
        try {
            val response = repository.getPokemonDetails(id)
            if (response != null) {
                emit(ResultData.Success(response))
            } else {
                emit(ResultData.Failure(NoSuchElementException("Pokémon não encontrado")))
            }
        } catch (e: Exception) {
            emit(ResultData.Failure(e))
        }
    }
}