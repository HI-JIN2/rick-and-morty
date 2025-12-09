package com.yujin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.usecase.SearchCharactersUseCase
import com.yujin.presentation.characterlist.model.toUiModel
import com.yujin.presentation.common.UiState
import com.yujin.presentation.common.toUiState
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

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    private val _searchStateFlow: MutableStateFlow<SearchState> = MutableStateFlow(UiState.Init)
    val searchStateFlow: StateFlow<SearchState> = _searchStateFlow.asStateFlow()

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
        _searchQueryFlow.value = query

        // 검색어가 비어있으면 결과 초기화
        if (query.isBlank()) {
            _searchStateFlow.value = UiState.Init
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            _searchStateFlow.value = UiState.Loading
            val filter = CharacterFilter(name = query)
            val uiState = searchCharactersUseCase(filter, page = 1)
                .toUiState { it.results.map { character -> character.toUiModel() } }
            _searchStateFlow.value = uiState
        }
    }
}