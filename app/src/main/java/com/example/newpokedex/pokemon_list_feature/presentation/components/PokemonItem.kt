package com.example.newpokedex.pokemon_list_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newpokedex.core.domain.model.Pokemon
import com.example.newpokedex.core.util.getHighResPokemonUrl
import com.example.newpokedex.core.util.parseTypeToColor
import com.example.newpokedex.ui.theme.ChromeWhite

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val typeColor = parseTypeToColor(pokemon.primaryType)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(22.dp),
                ambientColor = typeColor.copy(alpha = 0.5f),
                spotColor = typeColor
            )
            .clip(RoundedCornerShape(22.dp))
            .clickable { onPokemonClick(pokemon.id) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161A22))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            typeColor.copy(alpha = 0.55f),
                            typeColor.copy(alpha = 0.15f),
                            Color(0xFF10131A)
                        ),
                        radius = 450f
                    )
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.6f),
                            typeColor.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(14.dp)
        ) {
            // Número Gigante Translúcido de Fundo (Estilo Cyberpunk/Modern)
            Text(
                text = "#%03d".format(pokemon.id),
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                color = Color.White.copy(alpha = 0.08f),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 6.dp, y = (-8).dp)
            )

            // Círculo de Brilho / Aura atrás do Pokémon
            Box(
                modifier = Modifier
                    .size(95.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 6.dp, y = 6.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(typeColor.copy(alpha = 0.45f), Color.Transparent)
                        )
                    )
            )

            // Informações de Texto
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "#%03d".format(pokemon.id),
                        color = ChromeWhite.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = pokemon.name,
                        color = ChromeWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Chip com gradiente vibrante
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(typeColor.copy(alpha = 0.9f), typeColor.copy(alpha = 0.6f))
                            )
                        )
                        .border(0.8.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = pokemon.primaryType.replaceFirstChar { it.uppercase() },
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Imagem HD Oficial Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getHighResPokemonUrl(pokemon.id))
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(105.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 8.dp, y = 8.dp)
            )
        }
    }
}