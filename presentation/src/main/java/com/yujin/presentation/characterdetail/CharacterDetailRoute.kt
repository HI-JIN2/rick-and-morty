package com.yujin.presentation.characterdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yujin.presentation.common.UiState

/**
 * CharacterDetail Actions emitted from the UI Layer
 */
data class CharacterDetailActions(
    val onRetry: () -> Unit = {},
    val onBackClick: () -> Unit = {}
)

@Composable
fun CharacterDetailRoute(
    characterId: Int,
    onBackClick: () -> Unit = {},
    modifier: Modifier,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    // Load character on first composition
    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    // State observing and declarations
    val uiState by viewModel.stateFlow.collectAsState(UiState.Init)

    // UI Actions
    val actions = rememberCharacterDetailActions(viewModel, characterId, onBackClick)

    // UI Rendering
    CharacterDetailScreen(uiState, actions, modifier)
}


@Composable
fun rememberCharacterDetailActions(
    viewModel: CharacterDetailViewModel,
    characterId: Int,
    onBackClick: () -> Unit = {}
): CharacterDetailActions {
    return remember(viewModel, characterId, onBackClick) {
        CharacterDetailActions(
            onRetry = { viewModel.loadCharacter(characterId) },
            onBackClick = onBackClick
        )
    }
}
