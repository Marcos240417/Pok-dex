package com.example.newpokedex.core.domain.repository

import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonWithDetails
import kotlinx.coroutines.flow.Flow

/**
 * Interface PokemonRepository: Define as REGRAS do que o repositório deve fazer.
 * A ViewModel usará apenas esta interface, sem saber de onde os dados vêm.
 */
interface PokemonRepository {
    // Busca Pokémons na rede e salva no banco local
    suspend fun syncPokemons(limit: Int, offset: Int)

    // Observa a lista de Pokémons salvos no banco
    fun getPokemonList(): Flow<List<PokemonEntity>>

    // Busca os detalhes completos de um Pokémon específico
    suspend fun getPokemonDetails(id: Int): PokemonWithDetails?
}