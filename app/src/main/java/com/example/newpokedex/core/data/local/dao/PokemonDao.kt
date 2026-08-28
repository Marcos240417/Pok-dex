package com.example.newpokedex.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newpokedex.core.data.local.PokemonWithDetails
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonMoveEntity
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: List<PokemonStatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoves(moves: List<PokemonMoveEntity>)

    @Query("SELECT * FROM pokemons ORDER BY pokemonId ASC")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE pokemonId = :id")
    suspend fun getFullDetails(id: Int): PokemonWithDetails?

    // Busca por proximidade de Nome ou ID
    @Query("SELECT * FROM pokemons WHERE nome LIKE '%' || :query || '%' OR CAST(pokemonId AS TEXT) LIKE '%' || :query || '%' ORDER BY pokemonId ASC")
    fun searchPokemons(query: String): Flow<List<PokemonEntity>>
}