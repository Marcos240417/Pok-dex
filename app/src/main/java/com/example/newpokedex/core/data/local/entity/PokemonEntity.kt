package com.example.newpokedex.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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

/**
 * Tabela de Movimentos (Moves): Golpes que o Pokémon aprende.
 */
@Entity(tableName = "pokemon_moves")
data class PokemonMoveEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,   // Chave estrangeira
    val nomeMove: String  // Ex: "tackle", "thunder-shock"
)

/**
 * Tabela de Evoluções: Quem evolui para quem.
 */
@Entity(tableName = "pokemon_evolutions")
data class PokemonEvolutionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pokemonId: Int,      // Chave estrangeira
    val nomeEvolucao: String // Nome do próximo pokémon na linha
)


/**
 * PokemonWithDetails: Esta NÃO é uma tabela. É um POJO (Plain Old Java Object) de "Relacionamento".
 * Ela serve para o Room fazer um "JOIN" (união) automático entre várias tabelas.
 */
data class PokemonWithDetails(
    /**
     * @Embedded: Pega todos os campos da PokemonEntity e coloca dentro deste objeto.
     */
    @Embedded val pokemon: PokemonEntity,

    /**
     * @Relation: Cria o vínculo entre a tabela de Pokémons e as tabelas de detalhes.
     * parentColumn: O ID na tabela 'pokemons'.
     * entityColumn: O ID correspondente na outra tabela (ex: PokemonStatEntity).
     */
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val stats: List<PokemonStatEntity>, // Busca todos os status do Pokémon

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val moves: List<PokemonMoveEntity>, // Busca todos os golpes

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "pokemonId"
    )
    val evolutions: List<PokemonEvolutionEntity> // Busca a linha evolutiva
)