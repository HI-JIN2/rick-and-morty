package com.yujin.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier


@Composable
fun SearchRoute(
    modifier: Modifier,
    coordinator: SearchCoordinator = rememberSearchCoordinator()
) {
    // State observing and declarations
    val uiState by coordinator.screenStateFlow.collectAsState(SearchState())

    // UI Actions
    val actions = rememberSearchActions(coordinator)

    // UI Rendering
    SearchScreen(uiState, actions, modifier)
}


@Composable
fun rememberSearchActions(coordinator: SearchCoordinator): SearchActions {
    return remember(coordinator) {
        SearchActions(
            onSearchQueryChange = { query ->
                coordinator.updateSearchQuery(query)
            },
            onCharacterClick = { characterId ->
                // TODO: Navigate to character detail
            }
        )
    }
}
