package com.example.newpokedex.core.remote.mapper

import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.data.local.entity.PokemonMoveEntity
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity
import com.example.newpokedex.core.remote.dto.PokemonDto

/**
 * ARQUIVO: PokemonMapper.kt
 * OBJETIVO: Este arquivo contém funções de extensão que servem como "tradutores".
 * Elas pegam os objetos DTO (Data Transfer Object) vindos da internet e os
 * transformam em Entities (Entidades) que o banco de dados Room consegue entender.
 */

/**
 * 1. Converte os dados básicos do Pokémon para a tabela principal.
 * Serve para preencher a PokemonEntity (nome, altura, peso, imagem e tipos).
 */
fun PokemonDto.toEntity(): PokemonEntity {
    return PokemonEntity(
        // O ID da API é usado como PrimaryKey para evitar duplicatas
        pokemonId = this.id,

        // Formata o nome para que a primeira letra seja sempre maiúscula na tela
        nome = this.name.replaceFirstChar { it.uppercase() },

        altura = this.height,
        peso = this.weight,

        // Pega a URL da imagem que mapeamos dentro do objeto 'sprites'
        urlImagem = this.sprites.imageUrl,

        /**
         * Extração de Tipos:
         * A API manda uma lista. Pegamos o primeiro item para 'tipoPrincipal'.
         * Se houver um segundo item, salvamos em 'tipoSecundario', senão fica null.
         */
        tipoPrincipal = this.types.getOrNull(0)?.type?.name ?: "desconhecido",
        tipoSecundario = this.types.getOrNull(1)?.type?.name,


    )
}

/**
 * 2. Converte a lista de Status (HP, Attack, etc.) para a tabela 'pokemon_stats'.
 * Retorna uma lista de entidades vinculadas ao ID deste Pokémon.
 */
fun PokemonDto.toStatsEntities(): List<PokemonStatEntity> {
    // O '.map' percorre cada status que veio da API
    return this.stats.map { dto ->
        PokemonStatEntity(
            pokemonId = this.id, // Chave estrangeira para o relacionamento
            nomeStat = dto.stat.name,
            valorBase = dto.base_stat
        )
    }
}

/**
 * 3. Converte a lista de Movimentos (Golpes) para a tabela 'pokemon_moves'.
 * Filtra apenas o nome do golpe para economizar espaço no banco local.
 */
fun PokemonDto.toMovesEntities(): List<PokemonMoveEntity> {
    return this.moves.map { dto ->
        PokemonMoveEntity(
            pokemonId = this.id, // Chave estrangeira
            nomeMove = dto.move.name
        )
    }
}