package com.yujin.presentation.characterlist

import androidx.paging.compose.LazyPagingItems
import com.yujin.presentation.characterlist.model.CharacterUiModel

/**
 * UI State that represents CharacterListScreen
 * Paging의 경우 LazyPagingItems가 이미 상태를 포함하므로 별도 UiState 불필요
 **/
data class CharacterListState(
    val pagingItems: LazyPagingItems<CharacterUiModel>
)

/**
 * CharacterList Events emitted from the UI Layer
 * passed to the coordinator to handle
 **/
sealed interface CharacterListEvent {
    data class NavigateToDetail(val characterId: Int) : CharacterListEvent
}

/**
 * CharacterList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterListActions(
    val onCharacterClick: (Int) -> Unit = {}
)
