package com.yujin.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.yujin.domain.repository.CharacterRepository
import com.yujin.presentation.characterlist.paging.CharacterPagingSource
import com.yujin.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharacterPagingSource(characterRepository) }
    ).flow
        .cachedIn(viewModelScope)

    private val _stateFlow: MutableStateFlow<CharacterListState> =
        MutableStateFlow(UiState.Init)

    val stateFlow: StateFlow<CharacterListState> = _stateFlow.asStateFlow()

    init {
        _stateFlow.value = UiState.Success(Unit)
    }
}