package com.example.newpokedex.core.remote.dto

import com.google.gson.annotations.SerializedName

data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val types: List<Type>,
    val stats: List<Stat>,
    val moves: List<Move>
)

//‘Sprites’ é referência própria da API para se referir as imagens oficiais
//Usei @SerializedName para sumir os ávios
data class Sprites(
    @SerializedName("front_default")
    val imageUrl: String
)

data class Type(
    val type: NamedResourceDto
)

data class Stat(
    @SerializedName("base_stat")
    val basestat: Int,
    val stat: NamedResourceDto
)

data class Move(
    val move: NamedResourceDto
)
