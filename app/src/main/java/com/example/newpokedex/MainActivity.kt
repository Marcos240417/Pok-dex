package com.example.newpokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
// CORREÇÃO: Certifique-se de que o import da Screen está correto
import com.example.newpokedex.feature.pokemonlist.PokemonScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Surface deve envolver a Screen para aplicar as cores do tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PokemonScreen() // Agora chamada dentro do contexto Composable
                }
            }
        }
    }
}