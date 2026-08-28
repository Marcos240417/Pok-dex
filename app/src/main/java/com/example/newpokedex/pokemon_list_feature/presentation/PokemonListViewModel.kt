package com.example.newpokedex.pokemon_list_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.newpokedex.pokemon_list_feature.domain.usecase.GetPokemonListUseCase
import com.example.newpokedex.pokemon_list_feature.domain.usecase.SearchPokemonUseCase
import com.example.newpokedex.pokemon_list_feature.presentation.state.PokemonListState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonListState())
    val uiState: StateFlow<PokemonListState> = _uiState.asStateFlow()

    private val searchQueryFlow = MutableStateFlow("")

    init {
        fetchPokemons()
        setupSearchDebounce()
    }

    private fun fetchPokemons() {
        val pagingData = getPokemonListUseCase().cachedIn(viewModelScope)
        _uiState.value = _uiState.value.copy(pokemons = pagingData)
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        searchQueryFlow.value = query
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(searchResult = null)
        }
    }

    private fun setupSearchDebounce() {
        searchQueryFlow
            .debounce(250.milliseconds) // Resposta rápida enquanto digita
            .distinctUntilChanged()
            .onEach { query ->
                if (query.isNotBlank()) {
                    performSearch(query)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            searchPokemonUseCase(query).collect { result ->
                _uiState.value = _uiState.value.copy(searchResult = result)
            }
        }
    }
}