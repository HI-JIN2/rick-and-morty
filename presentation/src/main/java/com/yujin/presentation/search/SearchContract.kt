package com.yujin.presentation.search

import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.common.UiState

/**
 * UI State that represents SearchScreen
 * searchQuery is managed separately as user input (synchronous)
 * searchState represents async API call state (Init, Loading, Success, Error)
 **/
typealias SearchState = UiState<List<CharacterUiModel>>

/**
 * Search Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SearchActions(
    val onSearchQueryChange: (String) -> Unit = {},
    val onCharacterClick: (Int) -> Unit = {}
)
