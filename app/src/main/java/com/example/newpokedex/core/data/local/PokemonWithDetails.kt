package com.example.newpokedex.core.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonEvolutionEntity
import com.example.newpokedex.core.data.local.entity.PokemonMoveEntity
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity

/**
 * PokemonWithDetails: Esta NÃO é uma tabela. É um POJO (Plain Old Java Object) de "Relacionamento".
 * Ela serve para o Room fazer um "JOIN" (união) automático entre várias tabelas.
 */
data class PokemonWithDetails(
    /**
     * @Embedded: Pega todos os campos da PokemonEntity e coloca dentro deste objeto.
     */
    @Embedded val pokemon: PokemonEntity,

    /**
     * @Relation: Cria o vínculo entre a tabela de Pokémons e as tabelas de detalhes.
     * parentColumn: O ID na tabela 'pokemons'.
     * entityColumn: O ID correspondente na outra tabela (ex: PokemonStatEntity).
     */
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val stats: List<PokemonStatEntity>, // Busca todos os status do Pokémon

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val moves: List<PokemonMoveEntity>, // Busca todos os golpes

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val evolutions: List<PokemonEvolutionEntity> // Busca a linha evolutiva
)