package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun CharacterListRoute(
    modifier: Modifier,
    onDetailClick: (Int) -> Unit = {},
    coordinator: CharacterListCoordinator = rememberCharacterListCoordinator()
) {
    // State observing and declarations
    val pagingItems = coordinator.charactersFlow.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberCharacterListActions(onDetailClick)

    // UI Rendering
    CharacterListScreen(
        pagingItems = pagingItems,
        actions = actions,
        modifier = modifier
    )
}


@Composable
fun rememberCharacterListActions(
    onDetailClick: (Int) -> Unit = {}
): CharacterListActions {
    return remember(onDetailClick) {
        CharacterListActions(
            onCharacterClick = onDetailClick
        )
    }
}
