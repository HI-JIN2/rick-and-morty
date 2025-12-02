package com.yujin.presentation.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<CharacterDetailState> =
        MutableStateFlow(CharacterDetailState())

    val stateFlow: StateFlow<CharacterDetailState> = _stateFlow.asStateFlow()


}