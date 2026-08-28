package com.example.newpokedex.pokemon_detail_feature.data.repository

import com.example.newpokedex.core.data.local.PokemonWithDetails
import com.example.newpokedex.core.data.local.dao.PokemonDao
import com.example.newpokedex.core.data.local.entity.PokemonMoveEntity
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity
import com.example.newpokedex.core.remote.apiservice.PokeApiService
import com.example.newpokedex.pokemon_detail_feature.domain.repository.PokemonDetailRepository
import com.example.newpokedex.pokemon_list_feature.data.mapper.toDomain
import com.example.newpokedex.pokemon_list_feature.data.mapper.toEntity

class PokemonDetailRepositoryImpl(
    private val dao: PokemonDao,
    private val api: PokeApiService
) : PokemonDetailRepository {

    override suspend fun getPokemonDetails(id: Int): PokemonWithDetails? {
        // 1. Tenta buscar localmente
        val localDetails = dao.getFullDetails(id)

        // Se já possui os dados completos no Room, retorna direto
        if (localDetails != null && localDetails.stats.isNotEmpty()) {
            return localDetails
        }

        // 2. Se não tiver stats salvos, busca na PokeAPI
        return try {
            val dto = api.getPokemonDetails(id.toString())
            val pokemonDomain = dto.toDomain()

            // Salva a entidade principal
            dao.insertPokemon(pokemonDomain.toEntity())

            // Mapeia e salva as estatísticas na tabela correta
            val statsEntities = dto.stats.map { statDto ->
                PokemonStatEntity(
                    pokemonId = dto.id,
                    nomeStat = statDto.stat.name,
                    valorBase = statDto.basestat
                )
            }
            dao.insertStats(statsEntities)

            // Mapeia e salva os movimentos
            val movesEntities = dto.moves.map { moveDto ->
                PokemonMoveEntity(
                    pokemonId = dto.id,
                    nomeMove = moveDto.move.name
                )
            }
            dao.insertMoves(movesEntities)

            // Retorna os dados completos recém-gravados
            dao.getFullDetails(id)
        } catch (e: Exception) {
            localDetails
        }
    }
}