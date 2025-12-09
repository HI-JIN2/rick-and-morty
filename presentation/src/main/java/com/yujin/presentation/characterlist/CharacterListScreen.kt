package com.yujin.presentation.characterlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.common.components.CharacterItem
import com.yujin.presentation.common.components.ErrorStateItem
import com.yujin.presentation.common.components.LoadingIndicatorItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharacterListScreen(
    pagingItems: LazyPagingItems<CharacterUiModel>,
    actions: CharacterListActions,
    modifier: Modifier,
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
        Box(modifier = modifier.padding(innerPadding)) {
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
                        LoadingIndicatorItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                // 에러 상태 표시
                if (pagingItems.loadState.append is LoadState.Error) {
                    item {
                        val error = (pagingItems.loadState.append as LoadState.Error).error
                        ErrorStateItem(
                            error = error,
                            onRetry = { pagingItems.retry() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }
            }

            // 초기 로딩 상태
            if (pagingItems.loadState.refresh is LoadState.Loading) {
                LoadingIndicatorItem(
                    modifier = Modifier.fillMaxSize()
                )
            }

            // 초기 에러 상태
            if (pagingItems.loadState.refresh is LoadState.Error) {
                val error = (pagingItems.loadState.refresh as LoadState.Error).error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorStateItem(
                        error = error,
                        onRetry = { pagingItems.retry() },
                        spacing = 16.dp
                    )
                }
            }
        }
    }
}