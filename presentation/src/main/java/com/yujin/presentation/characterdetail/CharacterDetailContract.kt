package com.yujin.presentation.characterdetail

import com.yujin.domain.model.Character
import com.yujin.presentation.common.UiState

/**
 * UI State that represents CharacterDetailScreen
 **/
typealias CharacterDetailState = UiState<Character>

/**
 * CharacterDetail Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterDetailActions(
    val onRetry: () -> Unit = {}
)


