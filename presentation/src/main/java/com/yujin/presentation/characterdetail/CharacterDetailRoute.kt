package com.yujin.presentation.characterdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.yujin.presentation.common.UiState


@Composable
fun CharacterDetailRoute(
    characterId: Int,
    coordinator: CharacterDetailCoordinator = rememberCharacterDetailCoordinator(characterId)
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(UiState.Init)

    // UI Actions
    val actions = rememberCharacterDetailActions(coordinator)

    // UI Rendering
    CharacterDetailScreen(uiState, actions)
}


@Composable
fun rememberCharacterDetailActions(coordinator: CharacterDetailCoordinator): CharacterDetailActions {
    return remember(coordinator) {
        CharacterDetailActions(
            onRetry = { coordinator.retry() }
        )
    }
}
