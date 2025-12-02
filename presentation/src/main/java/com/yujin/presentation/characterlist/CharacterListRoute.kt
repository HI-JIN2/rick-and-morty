package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun CharacterListRoute(
    coordinator: CharacterListCoordinator = rememberCharacterListCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(CharacterListState())

    // UI Actions
    val actions = rememberCharacterListActions(coordinator)

    // UI Rendering
    CharacterListScreen(uiState, actions)
}


@Composable
fun rememberCharacterListActions(coordinator: CharacterListCoordinator): CharacterListActions {
    return remember(coordinator) {
        CharacterListActions(

        )
    }
}
