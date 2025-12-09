package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import com.yujin.presentation.characterlist.model.CharacterUiModel
import kotlinx.coroutines.flow.Flow

/**
 * Screen's coordinator which is responsible for handling actions from the UI layer
 * and one-shot actions based on the new UI state
 */
class CharacterListCoordinator(
    val viewModel: CharacterListViewModel
) {
    val charactersFlow: Flow<PagingData<CharacterUiModel>> = viewModel.characters
}

@Composable
fun rememberCharacterListCoordinator(
    viewModel: CharacterListViewModel = hiltViewModel()
): CharacterListCoordinator {
    return remember(viewModel) {
        CharacterListCoordinator(
            viewModel = viewModel
        )
    }
}
