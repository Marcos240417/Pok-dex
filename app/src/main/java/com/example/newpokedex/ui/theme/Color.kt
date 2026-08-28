package com.example.newpokedex.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Cores Base Metálicas
val MetallicDark = Color(0xFF121418)
val MetallicCard = Color(0xFF1E222B)
val ChromeWhite = Color(0xFFF0F4F8)
val TitaniumSilver = Color(0xFF8C93A8)

// Cores de Tipos Pokémon
val TypeNormal = Color(0xFFA8A878)
val TypeFire = Color(0xFFFF5722)
val TypeWater = Color(0xFF00B0FF)
val TypeElectric = Color(0xFFFFD600)
val TypeGrass = Color(0xFF00E676)
val TypeIce = Color(0xFF00E5FF)
val TypeFighting = Color(0xFFFF1744)
val TypePoison = Color(0xFFD500F9)
val TypeGround = Color(0xFFFFB300)
val TypeFlying = Color(0xFF8C9EFF)
val TypePsychic = Color(0xFFFF4081)
val TypeBug = Color(0xFF76FF03)
val TypeRock = Color(0xFFBCAAA4)
val TypeGhost = Color(0xFF7C4DFF)
val TypeDragon = Color(0xFF651FFF)
val TypeDark = Color(0xFF3E2723)
val TypeSteel = Color(0xFFB0BEC5)
val TypeFairy = Color(0xFFFF80AB)

// Cores de Estatísticas Base (Stats)
val HPColor = Color(0xFFFF5252)
val AtkColor = Color(0xFFFF7A00)
val DefColor = Color(0xFFFFD600)
val SpAtkColor = Color(0xFF00E5FF)
val SpDefColor = Color(0xFF00E676)
val SpdColor = Color(0xFFFF4081)

// Gradientes Metálicos
fun metallicChromeBrush() = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFFFFF),
        Color(0xFFB0BEC5),
        Color(0xFFECEFF1),
        Color(0xFF90A4AE)
    )
)

fun metallicGoldBrush() = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFDF00),
        Color(0xFFD4AF37),
        Color(0xFFFFF275),
        Color(0xFFAA771C)
    )
)

fun metallicCardBrush(dominantColor: Color) = Brush.verticalGradient(
    colors = listOf(
        dominantColor.copy(alpha = 0.45f),
        MetallicCard.copy(alpha = 0.95f),
        MetallicDark
    )
)