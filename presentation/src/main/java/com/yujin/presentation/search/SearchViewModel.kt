package com.yujin.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.usecase.SearchCharactersUseCase
import com.yujin.presentation.characterlist.model.CharacterUiModel
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

/**
 * UI State that represents SearchScreen
 * searchQuery is managed separately as user input (synchronous)
 * searchState represents async API call state (Init, Loading, Success, Error)
 */
typealias SearchState = UiState<List<CharacterUiModel>>

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    companion object {
        private const val DEBOUNCE_DELAY_MS = 500L
        private const val MIN_SEARCH_QUERY_LENGTH = 3
    }

    private val _searchQueryFlow = MutableStateFlow("")
    val searchQueryFlow: StateFlow<String> = _searchQueryFlow.asStateFlow()

    private val _searchStateFlow: MutableStateFlow<SearchState> = MutableStateFlow(UiState.Init)
    val searchStateFlow: StateFlow<SearchState> = _searchStateFlow.asStateFlow()

    init {
        // Debounce 검색어 입력
        _searchQueryFlow
            .debounce(DEBOUNCE_DELAY_MS)
            .distinctUntilChanged()
            .filter { it.length >= MIN_SEARCH_QUERY_LENGTH }
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

    fun retry() {
        val currentQuery = _searchQueryFlow.value
        if (currentQuery.isNotBlank()) {
            searchCharacters(currentQuery)
        }
    }

    private fun searchCharacters(query: String) {
        viewModelScope.launch {
            _searchStateFlow.value = UiState.Loading
            val filter = CharacterFilter(name = query)
            val characterSearchResult = searchCharactersUseCase(filter, page = 1)
                .toUiState { it.results.map { character -> character.toUiModel() } }
            _searchStateFlow.value = characterSearchResult
        }
    }
}