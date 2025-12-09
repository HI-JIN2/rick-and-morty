package com.yujin.presentation.search

import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.common.UiState

/**
 * UI State that represents SearchScreen
 **/
data class SearchState(
    val searchQuery: String = "",
    val searchResults: UiState<List<CharacterUiModel>> = UiState.Init
)

/**
 * Search Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SearchActions(
    val onSearchQueryChange: (String) -> Unit = {},
    val onCharacterClick: (Int) -> Unit = {}
)


