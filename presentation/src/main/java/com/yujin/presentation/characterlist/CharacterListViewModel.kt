package com.yujin.presentation.characterlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CharacterListState> =
        MutableStateFlow(CharacterListState())

    val stateFlow: StateFlow<CharacterListState> = _stateFlow.asStateFlow()


}