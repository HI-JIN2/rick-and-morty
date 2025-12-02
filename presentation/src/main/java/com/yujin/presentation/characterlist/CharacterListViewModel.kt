package com.yujin.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.domain.usecase.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CharacterListState> =
        MutableStateFlow(CharacterListState())

    val stateFlow: StateFlow<CharacterListState> = _stateFlow.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters(page: Int = 1) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true, error = null) }

            getAllCharactersUseCase(page).fold(
                onSuccess = { response ->
                    _stateFlow.update { currentState ->
                        currentState.copy(
                            characters = if (page == 1) {
                                response.results
                            } else {
                                currentState.characters + response.results
                            },
                            isLoading = false,
                            currentPage = page,
                            hasNextPage = response.info.next != null
                        )
                    }
                },
                onFailure = { error ->
                    _stateFlow.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Unknown error"
                        )
                    }
                }
            )
        }
    }

    fun loadMore() {
        val currentState = _stateFlow.value
        if (!currentState.isLoading && currentState.hasNextPage) {
            loadCharacters(currentState.currentPage + 1)
        }
    }

    fun retry() {
        loadCharacters(_stateFlow.value.currentPage)
    }
}