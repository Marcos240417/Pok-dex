package com.example.newpokedex.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val pokemonId: Int,
    val nome: String,
    val altura: Int,
    val peso: Int,
    val urlImagem: String,
    // Armazenamos os tipos como Strings simples para facilitar filtros e cores na lista.
    val tipoPrincipal: String,
    val tipoSecundario: String?, // O '?' indica que o Pokémon pode ter apenas um tipo.,
)