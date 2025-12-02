package com.yujin.domain.usecase

import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository

class GetAllCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<CharacterResponse> {
        return repository.getAllCharacters(page)
    }
}

