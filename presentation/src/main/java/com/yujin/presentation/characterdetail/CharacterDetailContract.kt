package com.yujin.presentation.characterdetail

import com.yujin.presentation.characterdetail.model.CharacterDetailUiModel
import com.yujin.presentation.common.UiState

/**
 * UI State that represents CharacterDetailScreen
 **/
typealias CharacterDetailState = UiState<CharacterDetailUiModel>

/**
 * CharacterDetail Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterDetailActions(
    val onRetry: () -> Unit = {},
    val onBackClick: () -> Unit = {}
)


