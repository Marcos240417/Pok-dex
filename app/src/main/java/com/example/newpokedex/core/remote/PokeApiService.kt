package com.example.newpokedex.core.remote

import com.example.newpokedex.core.remote.dto.PokemonDto
import com.example.newpokedex.core.remote.dto.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponseDto

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): PokemonDto
}