package com.yujin.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yujin.designsystem.theme.RickAndMortyTheme
import com.yujin.presentation.navigation.RickAndMortyNavigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                RickAndMortyNavigation()
            }
        }
    }
}
