package com.yujin.presentation.characterlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.yujin.domain.model.Character
import com.yujin.presentation.characterlist.components.CharacterItem
import com.yujin.presentation.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    state: UiState<Unit>,
    actions: CharacterListActions,
    pagingItems: LazyPagingItems<Character>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Character List")
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.id }
                ) { index ->
                    val character = pagingItems[index]
                    if (character != null) {
                        CharacterItem(
                            character = character,
                            onClick = { actions.onCharacterClick(character.id) }
                        )
                    }
                }

                // 로딩 상태 표시
                if (pagingItems.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                // 에러 상태 표시
                if (pagingItems.loadState.append is LoadState.Error) {
                    item {
                        val error = (pagingItems.loadState.append as LoadState.Error).error
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Error: ${error.message}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Retry",
                                modifier = Modifier.clickable { pagingItems.retry() }
                            )
                        }
                    }
                }
            }

            // 초기 로딩 상태
            if (pagingItems.loadState.refresh is LoadState.Loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            // 초기 에러 상태
            if (pagingItems.loadState.refresh is LoadState.Error) {
                val error = (pagingItems.loadState.refresh as LoadState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: ${error.message}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Retry",
                            modifier = Modifier.clickable { pagingItems.retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "CharacterList")
private fun CharacterListScreenPreview() {
    // Preview는 PagingItems 없이 표시할 수 없으므로 제거
}
