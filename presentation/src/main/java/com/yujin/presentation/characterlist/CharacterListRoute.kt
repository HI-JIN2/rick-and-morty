package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.yujin.presentation.common.UiState


@Composable
fun CharacterListRoute(
    modifier: Modifier,
    coordinator: CharacterListCoordinator = rememberCharacterListCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(UiState.Init)
    val pagingItems = coordinator.characters.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberCharacterListActions(coordinator)

    // UI Rendering
    CharacterListScreen(uiState, actions, pagingItems, modifier)
}


@Composable
fun rememberCharacterListActions(coordinator: CharacterListCoordinator): CharacterListActions {
    return remember(coordinator) {
        CharacterListActions(
            onCharacterClick = { characterId ->
                coordinator.handleEvent(CharacterListEvent.NavigateToDetail(characterId))
            }
        )
    }
}
