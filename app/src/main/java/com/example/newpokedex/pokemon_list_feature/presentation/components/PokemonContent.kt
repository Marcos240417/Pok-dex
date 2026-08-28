package com.example.newpokedex.pokemon_list_feature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.newpokedex.core.domain.model.Pokemon

@Composable
fun PokemonContent(
    pokemons: LazyPagingItems<Pokemon>,
    onPokemonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Box(
        modifier = modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0F1116), Color(0xFF08090C))
                )
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(
                count = pokemons.itemCount,
                key = { index -> pokemons[index]?.id ?: index }
            ) { index ->
                pokemons[index]?.let { pokemon ->
                    PokemonItem(
                        pokemon = pokemon,
                        onPokemonClick = onPokemonClick
                    )
                }
            }

            if (pokemons.loadState.append is LoadState.Loading) {
                item(span = { GridItemSpan(2) }) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        color = Color(0xFF00E5FF)
                    )
                }
            }
        }

        if (pokemons.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF00E5FF)
            )
        }

        if (pokemons.loadState.refresh is LoadState.Error) {
            val error = (pokemons.loadState.refresh as LoadState.Error).error
            Button(
                onClick = { pokemons.retry() },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text("Erro: ${error.localizedMessage ?: "Tentar novamente"}")
            }
        }
    }
}