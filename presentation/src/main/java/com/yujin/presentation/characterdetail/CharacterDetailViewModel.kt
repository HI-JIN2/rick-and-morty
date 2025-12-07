package com.yujin.presentation.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.core.model.ApiResult
import com.yujin.domain.usecase.GetCharacterByIdUseCase
import com.yujin.presentation.characterdetail.model.toDetailUiModel
import com.yujin.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CharacterDetailState> =
        MutableStateFlow(UiState.Init)

    val stateFlow: StateFlow<CharacterDetailState> = _stateFlow.asStateFlow()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _stateFlow.value = UiState.Loading
            when (val result = getCharacterByIdUseCase(characterId)) {
                is ApiResult.Success -> {
                    _stateFlow.value = UiState.Success(result.data.toDetailUiModel())
                }

                is ApiResult.Failure -> {
                    _stateFlow.value = UiState.Error(
                        throwable = Throwable(
                            "Server error: ${result.responseCode} - ${result.message ?: "Unknown error"}"
                        )
                    )
                }

                is ApiResult.NetworkError -> {
                    _stateFlow.value = UiState.Error(throwable = result.exception)
                }

                is ApiResult.UnknownError -> {
                    _stateFlow.value = UiState.Error(throwable = result.exception)
                }
            }
        }
    }
}