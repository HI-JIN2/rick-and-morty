package com.yujin.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yujin.presentation.common.UiState

/**
 * Search Actions emitted from the UI Layer
 */
data class SearchActions(
    val onSearchQueryChange: (String) -> Unit = {},
    val onCharacterClick: (Int) -> Unit = {}
)

@Composable
fun SearchRoute(
    modifier: Modifier,
    onCharacterClick: (Int) -> Unit = {},
    viewModel: SearchViewModel = hiltViewModel()
) {
    // State observing and declarations
    val searchQuery by viewModel.searchQueryFlow.collectAsState("")
    val searchState by viewModel.searchStateFlow.collectAsState(UiState.Init)

    // UI Actions
    val actions = rememberSearchActions(viewModel, onCharacterClick)

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
    viewModel: SearchViewModel,
    onCharacterClick: (Int) -> Unit = {}
): SearchActions {
    return remember(viewModel, onCharacterClick) {
        SearchActions(
            onSearchQueryChange = { query ->
                viewModel.updateSearchQuery(query)
            },
            onCharacterClick = onCharacterClick
        )
    }
}
