package com.yujin.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yujin.designsystem.Dimens
import com.yujin.designsystem.theme.RickAndMortyTheme
import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.common.UiState
import com.yujin.presentation.common.components.CharacterItem
import com.yujin.presentation.common.components.ErrorStateItem
import com.yujin.presentation.common.components.LoadingIndicatorItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchState: SearchState,
    actions: SearchActions,
    modifier: Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Search")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = actions.onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.ScreenPadding),
                placeholder = { Text("Search by name") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "검색어 삭제",
                            modifier = Modifier.clickable {
                                actions.onSearchQueryChange("")
                            })
                    }
                },
                singleLine = true
            )

            // Search Results
            when (searchState) {
                is UiState.Init -> {
                    // 초기 상태 - 검색어 입력 대기
                }

                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicatorItem()
                    }
                }

                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(
                            horizontal = Dimens.ScreenPadding,
                            vertical = Dimens.SpacingSmall
                        ),
                        verticalArrangement = Arrangement.spacedBy(Dimens.ListItemSpacing)
                    ) {
                        items(
                            items = searchState.data,
                            key = { it.id }
                        ) { character ->
                            CharacterItem(
                                character = character,
                                onClick = { actions.onCharacterClick(character.id) }
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorStateItem(
                            error = searchState.throwable,
                            onRetry = actions.onRetry,
                            spacing = Dimens.ErrorSpacing
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_Initial() {
    RickAndMortyTheme {
        SearchScreen(
            searchQuery = "Rick",
            searchState = UiState.Init,
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_Loading() {
    RickAndMortyTheme {
        SearchScreen(
            searchQuery = "Rick",
            searchState = UiState.Loading,
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_NoResults() {
    RickAndMortyTheme {
        SearchScreen(
            searchQuery = "NonExistentCharacter",
            searchState = UiState.Success(emptyList()),
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_Success() {
    val sampleCharacters = listOf(
        CharacterUiModel(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        ),
        CharacterUiModel(
            id = 2,
            name = "Morty Smith",
            status = "Alive",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
        )
    )
    RickAndMortyTheme {
        SearchScreen(
            searchQuery = "Rick",
            searchState = UiState.Success(sampleCharacters),
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview_Error() {
    RickAndMortyTheme {
        SearchScreen(
            searchQuery = "Error",
            searchState = UiState.Error(
                Throwable("Failed to search characters")
            ),
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}
