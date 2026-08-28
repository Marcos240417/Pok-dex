package com.example.newpokedex.core.util

import androidx.compose.ui.graphics.Color
import com.example.newpokedex.ui.theme.*

fun parseTypeToColor(type: String): Color {
    return when (type.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.DarkGray
    }
}

fun parseStatToAbbr(statName: String): String {
    return when (statName.lowercase()) {
        "hp" -> "HP"
        "attack" -> "ATK"
        "defense" -> "DEF"
        "special-attack" -> "SP.ATK"
        "special-defense" -> "SP.DEF"
        "speed" -> "SPD"
        else -> statName.uppercase().take(4)
    }
}

fun parseStatToColor(statName: String): Color {
    return when (statName.lowercase()) {
        "hp" -> Color(0xFFFF5252)
        "attack" -> Color(0xFFFF7A00)
        "defense" -> Color(0xFFFFD600)
        "special-attack" -> Color(0xFF00E5FF)
        "special-defense" -> Color(0xFF00E676)
        "speed" -> Color(0xFFFF4081)
        else -> Color.White
    }
}

// URL oficial da PokeAPI com arte HD limpa
fun getHighResPokemonUrl(pokemonId: Int): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
}