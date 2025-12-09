package com.yujin.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.yujin.presentation.common.UiState


@Composable
fun SearchRoute(
    modifier: Modifier,
    onCharacterClick: (Int) -> Unit = {},
    coordinator: SearchCoordinator = rememberSearchCoordinator()
) {
    // State observing and declarations
    val searchQuery by coordinator.searchQueryFlow.collectAsState("")
    val searchState by coordinator.searchStateFlow.collectAsState(UiState.Init)

    // UI Actions
    val actions = rememberSearchActions(coordinator, onCharacterClick)

    // UI Rendering
    SearchScreen(
        searchQuery = searchQuery,
        searchState = searchState,
        actions = actions,
        modifier = modifier
    )
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
