package com.example.newpokedex.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newpokedex.core.data.local.entity.PokemonEntity
import com.example.newpokedex.core.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokemonViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    // Lista observada pela UI
    private val _pokemonList = MutableStateFlow<List<PokemonEntity>>(emptyList())
    val pokemonList: StateFlow<List<PokemonEntity>> = _pokemonList.asStateFlow()

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Variáveis de controle para paginação
    private var currentOffset = 0
    private val pageLimit = 20

    init {
        // Inicia observando o banco local
        observeLocalDatabase()
        // Carrega a primeira página
        fetchPokemons()
    }

    /**
     * Observa o Flow do Room. Sempre que novos itens forem salvos pelo Repositório,
     * este bloco é disparado e a lista na UI é atualizada automaticamente.
     */
    private fun observeLocalDatabase() {
        viewModelScope.launch {
            repository.getPokemonList().collect { list ->
                _pokemonList.value = list
            }
        }
    }

    /**
     * Função principal de busca. Ela agora gerencia o offset automaticamente.
     */
    fun fetchPokemons() {
        // Evita múltiplas chamadas simultâneas
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                println("DEBUG_POKEDEX: Buscando página com offset $currentOffset...")

                // Roda a sincronização em background
                withContext(Dispatchers.IO) {
                    repository.syncPokemons(limit = pageLimit, offset = currentOffset)
                }

                // Se a busca deu certo, incrementamos o offset para a próxima chamada
                currentOffset += pageLimit
                println("DEBUG_POKEDEX: Sincronização concluída! Próximo offset: $currentOffset")

            } catch (e: Exception) {
                println("DEBUG_POKEDEX: Erro ao carregar página: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}