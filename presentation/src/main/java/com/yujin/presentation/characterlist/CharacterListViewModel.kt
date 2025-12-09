package com.yujin.presentation.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.yujin.domain.usecase.GetAllCharactersUseCase
import com.yujin.presentation.characterlist.model.CharacterUiModel
import com.yujin.presentation.characterlist.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

    val characters: Flow<PagingData<CharacterUiModel>> = getAllCharactersUseCase()
        .map { pagingData ->
            pagingData.map { character -> character.toUiModel() }
        }
        .cachedIn(viewModelScope)
}