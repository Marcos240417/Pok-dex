package com.example.newpokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkMetallicColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = MetallicDark,
    primaryContainer = Color(0xFF004D5A),
    onPrimaryContainer = Color(0xFF80F4FF),

    secondary = TitaniumSilver,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF2B303C),
    onSecondaryContainer = ChromeWhite,

    tertiary = Color(0xFFFFD600),
    onTertiary = Color.Black,

    background = MetallicDark,
    onBackground = ChromeWhite,

    surface = MetallicCard,
    onSurface = ChromeWhite,
    surfaceVariant = Color(0xFF282E3A),
    onSurfaceVariant = TitaniumSilver,

    outline = Color(0xFF3F4756)
)

private val LightMetallicColorScheme = lightColorScheme(
    primary = Color(0xFF00838F),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2EBF2),
    onPrimaryContainer = Color(0xFF00363D),

    secondary = Color(0xFF5C6B73),
    onSecondary = Color.White,

    tertiary = Color(0xFFC67D00),
    onTertiary = Color.White,

    background = Color(0xFFE9ECEF),
    onBackground = Color(0xFF191C1E),

    surface = Color(0xFFF8F9FA),
    onSurface = Color(0xFF191C1E),
    surfaceVariant = Color(0xFFDEE2E6),
    onSurfaceVariant = Color(0xFF40484C),

    outline = Color(0xFFB0BEC5)
)

@Composable
fun NewPokedexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkMetallicColorScheme else LightMetallicColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}