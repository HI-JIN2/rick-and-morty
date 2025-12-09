package com.yujin.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun SearchRoute(
    modifier: Modifier,
    onCharacterClick: (Int) -> Unit = {},
    coordinator: SearchCoordinator = rememberSearchCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(SearchState())

    // UI Actions
    val actions = rememberSearchActions(coordinator, onCharacterClick)

    // UI Rendering
    SearchScreen(uiState, actions, modifier)
}


@Composable
fun rememberSearchActions(
    coordinator: SearchCoordinator,
    onCharacterClick: (Int) -> Unit = {}
): SearchActions {
    return remember(coordinator, onCharacterClick) {
        SearchActions(
            onSearchQueryChange = { query ->
                coordinator.updateSearchQuery(query)
            },
            onCharacterClick = onCharacterClick
        )
    }
}
