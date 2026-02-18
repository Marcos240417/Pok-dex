package com.example.newpokedex.core.remote.dto

// Representa a resposta da lista inicial (Nível 1)
data class PokemonListResponseDto(
    val results: List<NamedResourceDto>
)
// Representa o dossiê completo do Pokémon (Nível 2)
data class NamedResourceDto(
    val name: String,
    val url: String
)
