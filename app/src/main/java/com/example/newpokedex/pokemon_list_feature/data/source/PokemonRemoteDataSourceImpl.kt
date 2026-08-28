package com.example.newpokedex.pokemon_list_feature.data.source

import com.example.newpokedex.core.data.local.dao.PokemonDao
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.remote.apiservice.PokeApiService
import com.example.newpokedex.pokemon_list_feature.data.mapper.toDomain
import com.example.newpokedex.pokemon_list_feature.data.mapper.toEntity
import com.example.newpokedex.pokemon_list_feature.domain.source.PokemonRemoteDataSource

class PokemonRemoteDataSourceImpl(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRemoteDataSource {

    override suspend fun getPokemons(limit: Int, offset: Int): List<Pokemon> {
        val listResponse = api.getPokemonList(limit, offset)
        val pokemons = listResponse.results.map { resource ->
            api.getPokemonDetails(resource.name).toDomain()
        }
        // Salva localmente para permitir busca por aproximação offline/instantânea
        dao.insertPokemons(pokemons.map { it.toEntity() })
        return pokemons
    }

    override suspend fun searchPokemon(query: String): Pokemon {
        return api.getPokemonDetails(query.trim().lowercase()).toDomain()
    }
}