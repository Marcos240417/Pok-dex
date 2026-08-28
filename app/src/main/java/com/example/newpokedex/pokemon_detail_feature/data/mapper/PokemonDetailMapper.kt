package com.example.newpokedex.pokemon_detail_feature.data.mapper

import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.domain.model.Pokemon

fun PokemonEntity.toDomain(): Pokemon {
    return Pokemon(
        id = this.pokemonId,
        name = this.nome,
        height = this.altura,
        weight = this.peso,
        imageUrl = this.urlImagem,
        primaryType = this.tipoPrincipal,
        secondaryType = this.tipoSecundario
    )
}