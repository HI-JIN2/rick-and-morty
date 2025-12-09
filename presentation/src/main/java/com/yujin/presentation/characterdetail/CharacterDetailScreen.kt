package com.yujin.presentation.characterdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.yujin.designsystem.Dimens
import com.yujin.designsystem.theme.RickAndMortyTheme
import com.yujin.presentation.R
import com.yujin.presentation.characterdetail.model.CharacterDetailUiModel
import com.yujin.presentation.common.UiState
import com.yujin.presentation.common.components.ErrorItem
import com.yujin.presentation.common.components.LoadingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    state: CharacterDetailState,
    actions: CharacterDetailActions,
    modifier: Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = (if (state is UiState.Success) state.data.name else stringResource(R.string.character_detail)))
                },
                navigationIcon = {
                    IconButton(onClick = actions.onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            when (state) {
                is UiState.Init -> {
                    // 초기 상태에서는 아무것도 표시하지 않음
                }

                is UiState.Loading -> {
                    LoadingItem(
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
                        ErrorItem(
                            error = state.throwable,
                            onRetry = actions.onRetry,
                            spacing = Dimens.ErrorSpacing
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterDetailContent(
    character: CharacterDetailUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpacingLarge)
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
                    .padding(Dimens.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpacingMedium)
            ) {

                // Status
                CharacterInfoRow(
                    label = stringResource(R.string.status),
                    value = character.status
                )

                // Gender
                CharacterInfoRow(
                    label = stringResource(R.string.gender),
                    value = character.gender
                )

                // Species
                CharacterInfoRow(
                    label = stringResource(R.string.species),
                    value = character.species
                )

                // Origin
                CharacterInfoRow(
                    label = stringResource(R.string.origin),
                    value = character.origin
                )

                // Location
                CharacterInfoRow(
                    label = stringResource(R.string.location),
                    value = character.location
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

@Preview(showBackground = true)
@Composable
private fun CharacterDetailScreenSuccessPreview() {
    val sampleCharacter = CharacterDetailUiModel(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        gender = "Male",
        image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        origin = "Earth (C-137)",
        location = "Citadel of Ricks"
    )
    RickAndMortyTheme {
        CharacterDetailScreen(
            state = UiState.Success(sampleCharacter),
            actions = CharacterDetailActions(onBackClick = {}, onRetry = {}),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailScreenLoadingPreview() {
    RickAndMortyTheme {
        CharacterDetailScreen(
            state = UiState.Loading,
            actions = CharacterDetailActions(onBackClick = {}, onRetry = {}),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterDetailScreenErrorPreview() {
    RickAndMortyTheme {
        CharacterDetailScreen(
            state = UiState.Error(Throwable("Preview Error")),
            actions = CharacterDetailActions(onBackClick = {}, onRetry = {}),
            modifier = Modifier
        )
    }
}
