package com.example.newpokedex.core.remote

import com.example.newpokedex.core.remote.dto.PokemonDto
import com.example.newpokedex.core.remote.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    /**
     * getPokemonList: Pede a lista básica de Pokémons.
     * @Query("limit"): Adiciona '?limit=10' na URL para dizer quantos queremos.
     * @Query("offset"): Adiciona '&offset=0' para pular os que já carregamos.
     * Retorna: PokemonListResponseDto (O Cardápio).
     */
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponseDto

    /**
     * getPokemonDetails: Pede os detalhes de um Pokémon específico.
     * @Path("name"): Substitui '{name}' na URL pelo nome real (ex: "pikachu").
     * Retorna: PokemonDto (O Dossiê Bruto).
     * IMPORTANTE: Nunca retorne Entity aqui, pois a API não conhece o seu banco de dados.
     */
    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDto
}