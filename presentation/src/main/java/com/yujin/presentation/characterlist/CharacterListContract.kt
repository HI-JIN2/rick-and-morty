package com.yujin.presentation.characterlist

import com.yujin.presentation.common.UiEvent
import com.yujin.presentation.common.UiState

/**
 * UI State that represents CharacterListScreen
 **/
typealias CharacterListState = UiState<Unit>

/**
 * CharacterList Events emitted from the UI Layer
 * passed to the coordinator to handle
 **/
sealed interface CharacterListEvent : UiEvent {
    data class NavigateToDetail(val characterId: Int) : CharacterListEvent
}

/**
 * CharacterList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterListActions(
    val onCharacterClick: (Int) -> Unit = {}
)
