package com.example.newpokedex.pokemon_list_feature.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newpokedex.core.util.ResultData
import com.example.newpokedex.pokemon_list_feature.presentation.components.PokemonContent
import com.example.newpokedex.pokemon_list_feature.presentation.components.PokemonItem
import com.example.newpokedex.pokemon_list_feature.presentation.components.PokemonSearchBar
import com.example.newpokedex.ui.theme.ChromeWhite
import com.example.newpokedex.ui.theme.TitaniumSilver
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(
    onPokemonClick: (Int) -> Unit = {},
    viewModel: PokemonListViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val pokemons = state.pokemons.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1116))
            .statusBarsPadding()
    ) {
        // TopBar / Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Pokédex",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = ChromeWhite
            )

            Spacer(modifier = Modifier.height(14.dp))

            PokemonSearchBar(
                query = state.searchQuery,
                onQueryChange = { query ->
                    viewModel.onSearchQueryChanged(query)
                }
            )
        }

        // Se estiver pesquisando, exibe a lista filtrada por aproximação
        if (state.searchQuery.isNotBlank()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val result = state.searchResult) {
                    is ResultData.Loading -> {
                        CircularProgressIndicator(
                            color = Color(0xFF00E5FF),
                            modifier = Modifier.padding(top = 40.dp)
                        )
                    }
                    is ResultData.Success -> {
                        if (result.data.isEmpty()) {
                            Text(
                                text = "Nenhum Pokémon encontrado com '${state.searchQuery}'",
                                color = TitaniumSilver,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(top = 40.dp)
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(vertical = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(14.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                items(result.data, key = { it.id }) { pokemon ->
                                    PokemonItem(
                                        pokemon = pokemon,
                                        onPokemonClick = onPokemonClick
                                    )
                                }
                            }
                        }
                    }
                    is ResultData.Failure -> {
                        Text(
                            text = "Erro ao buscar: ${result.exception.message}",
                            color = TitaniumSilver,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(top = 40.dp)
                        )
                    }
                    null -> {}
                }
            }
        } else {
            // Lista Paginada Padrão
            PokemonContent(
                pokemons = pokemons,
                onPokemonClick = onPokemonClick,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}