package com.example.newpokedex.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_evolutions")
data class PokemonEvolutionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,      // Chave estrangeira
    val nomeEvolucao: String // Nome do próximo pokémon na linha
)
