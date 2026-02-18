package com.example.newpokedex.feature.pokemonlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
// ADICIONE ESTE IMPORT (Verifique se o caminho do pacote está correto no seu projeto)
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.feature.PokemonViewModel
import org.koin.androidx.compose.koinViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = koinViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastIndex ->
                // Se o último item visível for um dos 2 últimos da lista atual, carrega mais
                if (lastIndex != null && lastIndex >= pokemonList.size - 2 && !isLoading) {
                    viewModel.fetchPokemons()
                }
            }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("New Pokedex") }) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            LazyColumn(
                state = listState, // VINCULAÇÃO: O LazyColumn agora usa o monitor de scroll
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = pokemonList) { pokemon: PokemonEntity ->
                    PokemonItem(name = pokemon.nome)
                }

                // INDICADOR DE CARREGAMENTO NO RODAPÉ
                if (isLoading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(name: String /*imageUrl: String*/) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        // Row organiza a imagem e o texto horizontalmente
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Alinha os itens ao centro vertical
        ) {
            /*// Componente do Coil para carregar a imagem da URL
            AsyncImage(
                model = imageUrl,
                contentDescription = "Imagem de $name",
                modifier = Modifier
                    .size(64.dp) // Define um tamanho fixo para a foto
                    .padding(end = 16.dp)
            )*/

            Text(
                text = name.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}