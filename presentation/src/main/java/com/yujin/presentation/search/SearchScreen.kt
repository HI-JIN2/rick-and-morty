package com.yujin.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.yujin.presentation.characterlist.components.CharacterItem
import com.yujin.presentation.characterlist.components.ErrorStateItem
import com.yujin.presentation.characterlist.components.LoadingIndicatorItem
import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.common.UiState
import com.yujin.presentation.ui.theme.RickAndMortyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    actions: SearchActions,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Search")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = actions.onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by name") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // Search Results
            when (val results = state.searchResults) {
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
                    if (results.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No results found")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                items = results.data,
                                key = { it.id }
                            ) { character ->
                                CharacterItem(
                                    character = character,
                                    onClick = { actions.onCharacterClick(character.id) }
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorStateItem(
                            error = results.throwable,
                            onRetry = { actions.onSearchQueryChange(state.searchQuery) },
                            spacing = 16.dp
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
            state = SearchState(
                searchQuery = "Rick",
                searchResults = UiState.Init
            ),
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
            state = SearchState(
                searchQuery = "Rick",
                searchResults = UiState.Loading
            ),
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
            state = SearchState(
                searchQuery = "NonExistentCharacter",
                searchResults = UiState.Success(emptyList())
            ),
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
            state = SearchState(
                searchQuery = "Rick",
                searchResults = UiState.Success(sampleCharacters)
            ),
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
            state = SearchState(
                searchQuery = "Error",
                searchResults = UiState.Error(
                    Throwable("Failed to search characters")
                )
            ),
            actions = SearchActions(),
            modifier = Modifier.fillMaxSize()
        )
    }
}
