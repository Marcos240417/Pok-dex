package com.example.newpokedex.pokemon_detail_feature.domain.repository

import com.example.newpokedex.core.data.local.PokemonWithDetails

interface PokemonDetailRepository {
    suspend fun getPokemonDetails(id: Int): PokemonWithDetails?
}