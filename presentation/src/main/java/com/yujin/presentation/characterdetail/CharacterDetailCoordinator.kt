package com.yujin.presentation.characterdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class CharacterDetailCoordinator(
    val viewModel: CharacterDetailViewModel,
    val characterId: Int
) {
    val screenStateFlow = viewModel.stateFlow

    init {
        viewModel.loadCharacter(characterId)
    }

    fun retry() {
        viewModel.loadCharacter(characterId)
    }
}

@Composable
fun rememberCharacterDetailCoordinator(
    characterId: Int,
    viewModel: CharacterDetailViewModel = hiltViewModel()
): CharacterDetailCoordinator {
    return remember(viewModel, characterId) {
        CharacterDetailCoordinator(
            viewModel = viewModel,
            characterId = characterId
        )
    }
}