package com.yujin.presentation.characterlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

/**
 * CharacterList Actions emitted from the UI Layer
 */
data class CharacterListActions(
    val onCharacterClick: (Int) -> Unit = {}
)

@Composable
fun CharacterListRoute(
    modifier: Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    // State observing and declarations
    val pagingItems = viewModel.characters.collectAsLazyPagingItems()

    // UI Actions
    val actions = rememberCharacterListActions(onDetailClick)

    // UI Rendering
    CharacterListScreen(
        pagingItems = pagingItems,
        actions = actions,
        modifier = modifier
    )
}


@Composable
fun rememberCharacterListActions(
    onDetailClick: (Int) -> Unit = {}
): CharacterListActions {
    return remember(onDetailClick) {
        CharacterListActions(
            onCharacterClick = onDetailClick
        )
    }
}
