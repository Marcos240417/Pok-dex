package com.example.newpokedex.core.presentation.navigation

sealed class Screen(val route: String) {
    data object PokemonList : Screen("pokemon_list")
    data object PokemonDetail : Screen("pokemon_detail/{pokemonId}") {
        fun passPokemonId(id: Int): String = "pokemon_detail/$id"
    }
}