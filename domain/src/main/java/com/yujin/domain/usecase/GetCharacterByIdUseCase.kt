package com.yujin.domain.usecase

import com.yujin.domain.model.Character
import com.yujin.domain.repository.CharacterRepository

class GetCharacterByIdUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Result<Character> {
        return repository.getCharacterById(id)
    }
}

