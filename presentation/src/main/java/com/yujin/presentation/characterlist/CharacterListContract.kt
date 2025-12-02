package com.yujin.presentation.characterlist

import com.yujin.domain.model.Character

/**
 * UI State that represents CharacterListScreen
 **/
data class CharacterListState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentPage: Int = 1,
    val hasNextPage: Boolean = true
)

/**
 * CharacterList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterListActions(
    val onCharacterClick: (Int) -> Unit = {},
    val onLoadMore: () -> Unit = {},
    val onRetry: () -> Unit = {}
)


