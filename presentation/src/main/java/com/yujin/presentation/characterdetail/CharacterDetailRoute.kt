package com.yujin.presentation.characterdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.yujin.presentation.common.UiState


@Composable
fun CharacterDetailRoute(
    characterId: Int,
    onBackClick: () -> Unit = {},
    modifier: Modifier,
    coordinator: CharacterDetailCoordinator = rememberCharacterDetailCoordinator()
) {
    // Load character on first composition
    LaunchedEffect(characterId) {
        coordinator.loadCharacter(characterId)
    }

    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(UiState.Init)

    // UI Actions
    val actions = rememberCharacterDetailActions(coordinator, characterId, onBackClick)

    // UI Rendering
    CharacterDetailScreen(uiState, actions, modifier)
}


@Composable
fun rememberCharacterDetailActions(
    coordinator: CharacterDetailCoordinator,
    characterId: Int,
    onBackClick: () -> Unit = {}
): CharacterDetailActions {
    return remember(coordinator, characterId, onBackClick) {
        CharacterDetailActions(
            onRetry = { coordinator.loadCharacter(characterId) },
            onBackClick = onBackClick
        )
    }
}
