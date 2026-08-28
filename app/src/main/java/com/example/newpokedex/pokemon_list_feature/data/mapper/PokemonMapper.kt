package com.example.newpokedex.pokemon_list_feature.data.mapper

import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.remote.dto.PokemonDto

fun PokemonDto.toDomain(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name.replaceFirstChar { it.uppercase() },
        height = this.height,
        weight = this.weight,
        imageUrl = this.sprites.imageUrl,
        primaryType = this.types.firstOrNull()?.type?.name ?: "normal",
        secondaryType = this.types.getOrNull(1)?.type?.name
    )
}

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        pokemonId = this.id,
        nome = this.name,
        altura = this.height,
        peso = this.weight,
        urlImagem = this.imageUrl,
        tipoPrincipal = this.primaryType,
        tipoSecundario = this.secondaryType
    )
}