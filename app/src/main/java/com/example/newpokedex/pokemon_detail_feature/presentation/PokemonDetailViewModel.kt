package com.example.newpokedex.pokemon_detail_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpokedex.core.data.local.PokemonWithDetails
import com.example.newpokedex.core.util.ResultData
import com.example.newpokedex.pokemon_detail_feature.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getDetailUseCase: GetPokemonDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ResultData<PokemonWithDetails>>(ResultData.Loading)
    val uiState: StateFlow<ResultData<PokemonWithDetails>> = _uiState.asStateFlow()

    fun loadPokemon(id: Int) {
        viewModelScope.launch {
            getDetailUseCase(id).collect { result ->
                _uiState.value = result
            }
        }
    }
}