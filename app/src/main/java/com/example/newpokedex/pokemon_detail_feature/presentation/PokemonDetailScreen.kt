package com.example.newpokedex.pokemon_detail_feature.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newpokedex.core.data.local.PokemonWithDetails
import com.example.newpokedex.core.data.local.entity.PokemonStatEntity
import com.example.newpokedex.core.util.*
import com.example.newpokedex.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    onBackClick: () -> Unit = {},
    viewModel: PokemonDetailViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonId) {
        viewModel.loadPokemon(pokemonId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C0E12))
    ) {
        // 1. Conteúdo de Dados / Loading / Erro
        when (val result = state) {
            is ResultData.Loading -> {
                CircularProgressIndicator(
                    color = Color(0xFF00E5FF),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is ResultData.Failure -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = result.exception.message ?: "Erro ao carregar Pokémon",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(onClick = { viewModel.loadPokemon(pokemonId) }) {
                        Text("Tentar Novamente")
                    }
                }
            }
            is ResultData.Success -> {
                PokemonDetailMetallicContent(details = result.data)
            }
        }

        // 2. Botão Flutuante de Voltar com Prioridade de Toque
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.5f))
                .border(1.dp, Color.White.copy(alpha = 0.25f), CircleShape)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun PokemonDetailMetallicContent(
    details: PokemonWithDetails,
    topPadding: Dp = 36.dp,
    pokemonImageSize: Dp = 230.dp
) {
    val primaryColor = remember(details.pokemon.tipoPrincipal) {
        parseTypeToColor(details.pokemon.tipoPrincipal)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        primaryColor.copy(alpha = 0.5f),
                        Color(0xFF14171F),
                        Color(0xFF0A0C0F)
                    )
                )
            )
    ) {
        // Card Metálico com Informações
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding + (pokemonImageSize / 2f), start = 16.dp, end = 16.dp, bottom = 16.dp)
                .shadow(20.dp, RoundedCornerShape(28.dp), spotColor = primaryColor)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF1E232E).copy(alpha = 0.95f),
                            Color(0xFF11141A)
                        )
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.verticalGradient(
                        listOf(Color.White.copy(alpha = 0.45f), primaryColor.copy(alpha = 0.3f), Color.Transparent)
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .align(Alignment.BottomCenter)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(pokemonImageSize / 2f + 14.dp))

                Text(
                    text = "#%03d %s".format(
                        details.pokemon.pokemonId,
                        details.pokemon.nome.replaceFirstChar { it.uppercase() }
                    ),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    color = ChromeWhite
                )

                Spacer(modifier = Modifier.height(16.dp))
                PokemonTypeChips(
                    primaryType = details.pokemon.tipoPrincipal,
                    secondaryType = details.pokemon.tipoSecundario
                )

                Spacer(modifier = Modifier.height(20.dp))
                PokemonPhysicalDataSection(
                    weight = details.pokemon.peso,
                    height = details.pokemon.altura,
                    accentColor = primaryColor
                )

                Spacer(modifier = Modifier.height(20.dp))
                PokemonStatsSection(stats = details.stats)
            }
        }

        // Imagem HD Oficial Coil com Aura de Fundo
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .size(pokemonImageSize + 20.dp)
                    .offset(y = topPadding - 10.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(primaryColor.copy(alpha = 0.4f), Color.Transparent)
                        )
                    )
            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getHighResPokemonUrl(details.pokemon.pokemonId))
                    .crossfade(true)
                    .build(),
                contentDescription = details.pokemon.nome,
                modifier = Modifier
                    .size(pokemonImageSize)
                    .offset(y = topPadding)
            )
        }
    }
}

@Composable
fun PokemonTypeChips(primaryType: String, secondaryType: String?) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TypeChip(typeName = primaryType)
        if (secondaryType != null) {
            Spacer(modifier = Modifier.width(12.dp))
            TypeChip(typeName = secondaryType)
        }
    }
}

@Composable
fun TypeChip(typeName: String) {
    val color = parseTypeToColor(typeName)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .background(
                Brush.horizontalGradient(
                    listOf(color.copy(alpha = 0.95f), color.copy(alpha = 0.7f))
                )
            )
            .border(1.2.dp, Color.White.copy(alpha = 0.6f), CircleShape)
            .shadow(6.dp, CircleShape, spotColor = color)
            .padding(horizontal = 24.dp, vertical = 6.dp)
    ) {
        Text(
            text = typeName.replaceFirstChar { it.uppercase() },
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PokemonPhysicalDataSection(weight: Int, height: Int, accentColor: Color) {
    val weightKg = weight / 10.0
    val heightMeters = height / 10.0

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF0F1218))
            .border(1.dp, accentColor.copy(alpha = 0.25f), RoundedCornerShape(18.dp))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$weightKg kg", color = ChromeWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Peso", color = TitaniumSilver, fontSize = 12.sp)
        }
        Box(modifier = Modifier.width(1.dp).height(35.dp).background(Color.White.copy(alpha = 0.15f)))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$heightMeters m", color = ChromeWhite, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Altura", color = TitaniumSilver, fontSize = 12.sp)
        }
    }
}

@Composable
fun PokemonStatsSection(stats: List<PokemonStatEntity>) {
    val distinctStats = remember(stats) { stats.distinctBy { it.nomeStat } }
    val maxStat = remember(distinctStats) {
        distinctStats.maxOfOrNull { it.valorBase }?.coerceAtLeast(100) ?: 100
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Estatísticas Base",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ChromeWhite
        )
        Spacer(modifier = Modifier.height(14.dp))

        distinctStats.forEachIndexed { index, stat ->
            AnimatedMetallicStatBar(
                statName = parseStatToAbbr(stat.nomeStat),
                statValue = stat.valorBase,
                statMaxValue = maxStat,
                statColor = parseStatToColor(stat.nomeStat),
                delayMillis = index * 80
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun AnimatedMetallicStatBar(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    delayMillis: Int
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercent by animateFloatAsState(
        targetValue = if (animationPlayed) (statValue / statMaxValue.toFloat()).coerceIn(0f, 1f) else 0f,
        animationSpec = tween(durationMillis = 900, delayMillis = delayMillis),
        label = "statAnimation"
    )

    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = statName,
            color = TitaniumSilver,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(60.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .height(16.dp)
                .clip(CircleShape)
                .background(Color(0xFF0F1218))
                .border(0.8.dp, Color.White.copy(alpha = 0.08f), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(curPercent)
                    .clip(CircleShape)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                statColor.copy(alpha = 0.7f),
                                statColor,
                                Color.White.copy(alpha = 0.9f)
                            )
                        )
                    )
            )
        }

        Text(
            text = statValue.toString(),
            color = ChromeWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.width(42.dp)
        )
    }
}