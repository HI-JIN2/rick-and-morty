package com.yujin.domain.usecase

import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository

class SearchCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(filter: CharacterFilter, page: Int = 1): Result<CharacterResponse> {
        return repository.searchCharacters(filter, page)
    }
}

