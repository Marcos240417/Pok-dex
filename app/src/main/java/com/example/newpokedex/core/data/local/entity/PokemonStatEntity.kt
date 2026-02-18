package com.example.newpokedex.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabela de Estatísticas (Stats): HP, Ataque, Defesa, etc.
 * Cada linha representa UM status de UM pokémon.
 */
@Entity(tableName = "pokemon_stats")
data class PokemonStatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID próprio da tabela
    val pokemonId: Int,   // Chave estrangeira que liga ao PokemonEntity
    val nomeStat: String, // Ex: "hp", "attack"
    val valorBase: Int    // Ex: 45, 60
)
