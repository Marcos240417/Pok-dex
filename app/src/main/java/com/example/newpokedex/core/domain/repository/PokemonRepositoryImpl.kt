package com.example.newpokedex.core.domain.repository

import com.example.newpokedex.core.dao.PokemonDao
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonWithDetails
import com.example.newpokedex.core.remote.PokeApiService
import com.example.newpokedex.core.remote.mapper.toEntity
import com.example.newpokedex.core.remote.mapper.toMovesEntities
import com.example.newpokedex.core.remote.mapper.toStatsEntities
import kotlinx.coroutines.flow.Flow

/**
 * Implementação real do Repository.
 * Ela coordena a PokeApiService (Rede) e o PokemonDao (Banco Local).
 */
class PokemonRepositoryImpl(
    private val api: PokeApiService,
    private val dao: PokemonDao
) : PokemonRepository {

    override suspend fun syncPokemons(limit: Int, offset: Int) {
        // Busca a lista de nomes da API
        val response = api.getPokemonList(limit, offset)

        // Para cada nome, busca os detalhes completos e salva no banco
        response.results.forEach { resource ->
            val dto = api.getPokemonDetails(resource.name)

            // Salva as três partes do Pokémon no banco de dados local
            dao.insertPokemon(dto.toEntity())
            dao.insertStats(dto.toStatsEntities())
            dao.insertMoves(dto.toMovesEntities())
        }
    }

    override fun getPokemonList(): Flow<List<PokemonEntity>> {
        return dao.getAllPokemons()
    }

    override suspend fun getPokemonDetails(id: Int): PokemonWithDetails? {
        return dao.getFullDetails(id)
    }
}