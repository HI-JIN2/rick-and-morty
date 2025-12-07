package com.yujin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.core.model.ApiResult
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.usecase.SearchCharactersUseCase
import com.yujin.presentation.characterlist.model.toUiModel
import com.yujin.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<SearchState> = MutableStateFlow(SearchState())

    val stateFlow: StateFlow<SearchState> = _stateFlow.asStateFlow()

    private val _searchQueryFlow = MutableStateFlow("")

    init {
        // Debounce 검색어 입력 (500ms)
        _searchQueryFlow
            .debounce(500)
            .distinctUntilChanged()
            .filter { it.isNotBlank() }
            .onEach { query ->
                searchCharacters(query)
            }
            .launchIn(viewModelScope)
    }

    fun updateSearchQuery(query: String) {
        _stateFlow.value = _stateFlow.value.copy(searchQuery = query)
        _searchQueryFlow.value = query

        // 검색어가 비어있으면 결과 초기화
        if (query.isBlank()) {
            _stateFlow.value = _stateFlow.value.copy(searchResults = UiState.Init)
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            _stateFlow.value = _stateFlow.value.copy(searchResults = UiState.Loading)
            val filter = CharacterFilter(name = query)
            val uiState = when (val result = searchCharactersUseCase(filter, page = 1)) {
                is ApiResult.Success -> {
                    val results = result.data.results.map { it.toUiModel() }
                    UiState.Success(results)
                }

                is ApiResult.Failure -> {
                    UiState.Error(
                        throwable = Throwable(
                            "Server error: ${result.responseCode} - ${result.message ?: "Unknown error"}"
                        )
                    )
                }

                is ApiResult.NetworkError -> {
                    UiState.Error(throwable = result.exception)
                }

                is ApiResult.UnknownError -> {
                    UiState.Error(throwable = result.exception)
                }
            }
            _stateFlow.value = _stateFlow.value.copy(searchResults = uiState)
        }
    }
}