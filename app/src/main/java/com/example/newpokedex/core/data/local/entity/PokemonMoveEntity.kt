package com.example.newpokedex.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabela de Movimentos (Moves): Golpes que o Pokémon aprende.
 */
@Entity(tableName = "pokemon_moves")
data class PokemonMoveEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,   // Chave estrangeira
    val nomeMove: String  // Ex: "tackle", "thunder-shock"
)
