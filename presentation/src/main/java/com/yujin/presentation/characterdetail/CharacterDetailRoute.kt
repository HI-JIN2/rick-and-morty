package com.yujin.presentation.characterdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun CharacterDetailRoute(
    coordinator: CharacterDetailCoordinator = rememberCharacterDetailCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(CharacterDetailState())

    // UI Actions
    val actions = rememberCharacterDetailActions(coordinator)

    // UI Rendering
    CharacterDetailScreen(uiState, actions)
}


@Composable
fun rememberCharacterDetailActions(coordinator: CharacterDetailCoordinator): CharacterDetailActions {
    return remember(coordinator) {
        CharacterDetailActions(

        )
    }
}
