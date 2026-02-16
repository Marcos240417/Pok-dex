package com.example.newpokedex.core.remote.dto

import com.google.gson.annotations.SerializedName
// Representa a resposta da lista inicial (Nível 1)
data class PokemonListResponseDto(
    val results: List<NamedResourceDto>
)
// Representa o item da lista (nome e link)
data class NamedResourceDto(
    val name: String,
    val url: String
)
// Representa o dossiê completo do Pokémon (Nível 2)
data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: SpritesDto,
    val types: List<TypeDto>,
    val stats: List<StatDto>,
    val moves: List<MoveDto>
)
data class SpritesDto(
    @SerializedName("front_default") val imageUrl: String
)
data class TypeDto(
    val type: NamedResourceDto
)
data class StatDto(
    val base_stat: Int,
    val stat: NamedResourceDto
)
data class MoveDto(
    val move: NamedResourceDto
)