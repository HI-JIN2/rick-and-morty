package com.yujin.domain.usecase

import com.yujin.core.model.ApiResult
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterList
import com.yujin.domain.repository.CharacterRepository
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(
        filter: CharacterFilter,
        page: Int = 1
    ): ApiResult<CharacterList> {
        return repository.searchCharacters(filter, page)
    }
}

