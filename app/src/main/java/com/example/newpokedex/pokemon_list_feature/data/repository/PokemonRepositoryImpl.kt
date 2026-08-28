package com.example.newpokedex.pokemon_list_feature.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newpokedex.core.data.local.dao.PokemonDao
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.paging.PokemonPagingSource
import com.example.newpokedex.core.util.ResultData
import com.example.newpokedex.pokemon_detail_feature.data.mapper.toDomain
import com.example.newpokedex.pokemon_list_feature.data.mapper.toDomain
import com.example.newpokedex.pokemon_list_feature.domain.repository.PokemonRepository
import com.example.newpokedex.pokemon_list_feature.domain.source.PokemonRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class PokemonRepositoryImpl(
    private val remoteDataSource: PokemonRemoteDataSource,
    private val dao: PokemonDao
) : PokemonRepository {

    override fun getPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = PokemonPagingSource.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 4
            ),
            pagingSourceFactory = { PokemonPagingSource(remoteDataSource) }
        ).flow
    }

    override fun searchPokemon(query: String): Flow<ResultData<List<Pokemon>>> = flow {
        emit(ResultData.Loading)
        try {
            dao.searchPokemons(query.trim().lowercase())
                .map { entities -> entities.map { it.toDomain() } }
                .collect { pokemons ->
                    emit(ResultData.Success(pokemons))
                }
        } catch (e: Exception) {
            emit(ResultData.Failure(e))
        }
    }
}