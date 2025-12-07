package com.yujin.presentation.characterdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.domain.usecase.GetCharacterByIdUseCase
import com.yujin.presentation.characterdetail.model.toDetailUiModel
import com.yujin.presentation.common.UiState
import com.yujin.presentation.common.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CharacterDetailState> =
        MutableStateFlow(UiState.Init)

    val stateFlow: StateFlow<CharacterDetailState> = _stateFlow.asStateFlow()

    fun loadCharacter(characterId: Int) {
        viewModelScope.launch {
            _stateFlow.value = UiState.Loading
            _stateFlow.value = getCharacterByIdUseCase(characterId)
                .toUiState { it.toDetailUiModel() }
        }
    }
}