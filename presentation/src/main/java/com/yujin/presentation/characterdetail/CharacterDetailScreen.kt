package com.yujin.presentation.characterdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yujin.presentation.characterlist.components.ErrorStateItem
import com.yujin.presentation.characterlist.components.LoadingIndicatorItem
import com.yujin.presentation.common.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    state: CharacterDetailState,
    actions: CharacterDetailActions
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Character Detail")
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is UiState.Init -> {
                    // 초기 상태에서는 아무것도 표시하지 않음
                }

                is UiState.Loading -> {
                    LoadingIndicatorItem(
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is UiState.Success -> {
                    CharacterDetailContent(
                        character = state.data,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        ErrorStateItem(
                            error = Throwable("Failed to load character"),
                            onRetry = actions.onRetry,
                            spacing = 16.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterDetailContent(
    character: com.yujin.domain.model.Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Character Image
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }

        // Character Information
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Name
                Text(
                    text = character.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Status
                CharacterInfoRow(
                    label = "Status",
                    value = character.status
                )

                // Gender
                CharacterInfoRow(
                    label = "Gender",
                    value = character.gender
                )

                // Species
                CharacterInfoRow(
                    label = "Species",
                    value = character.species
                )

                // Origin
                CharacterInfoRow(
                    label = "Origin",
                    value = character.origin.name
                )

                // Location
                CharacterInfoRow(
                    label = "Location",
                    value = character.location.name
                )
            }
        }
    }
}

@Composable
private fun CharacterInfoRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium
        )
        Text(text = value)
    }
}

