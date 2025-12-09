package com.yujin.domain.repository

import androidx.paging.PagingData
import com.yujin.core.model.ApiResult
import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterList
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): ApiResult<Character>
    suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int = 1
    ): ApiResult<CharacterList>
}

