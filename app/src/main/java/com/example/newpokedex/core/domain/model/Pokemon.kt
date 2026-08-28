package com.example.newpokedex.core.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val imageUrl: String,
    val primaryType: String,
    val secondaryType: String?
)