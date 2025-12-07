package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun CharacterListRoute(
    modifier: Modifier,
    onDetailClick: (Int) -> Unit = {},
    coordinator: CharacterListCoordinator = rememberCharacterListCoordinator(onDetailClick)
) {
    // State observing and declarations
    val pagingItems = coordinator.characters.collectAsLazyPagingItems()
    val uiState = CharacterListUiState(pagingItems = pagingItems)

    // UI Actions
    val actions = rememberCharacterListActions(coordinator)

    // UI Rendering
    CharacterListScreen(uiState, actions, modifier)
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
