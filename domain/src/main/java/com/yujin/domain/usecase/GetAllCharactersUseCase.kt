package com.yujin.domain.usecase

import androidx.paging.PagingData
import com.yujin.domain.model.Character
import com.yujin.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<PagingData<Character>> {
        return repository.getAllCharacters()
    }
}

