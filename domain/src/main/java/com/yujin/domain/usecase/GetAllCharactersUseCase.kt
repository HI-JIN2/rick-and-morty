package com.yujin.domain.usecase

import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int = 1): Result<CharacterResponse> {
        return repository.getAllCharacters(page)
    }
}

