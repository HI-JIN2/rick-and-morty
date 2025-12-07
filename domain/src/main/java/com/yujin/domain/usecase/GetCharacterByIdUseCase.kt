package com.yujin.domain.usecase

import com.yujin.core.model.ApiResult
import com.yujin.domain.model.Character
import com.yujin.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): ApiResult<Character> {
        return repository.getCharacterById(id)
    }
}

