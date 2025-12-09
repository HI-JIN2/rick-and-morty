package com.yujin.data.api

import com.yujin.core.model.ApiResult
import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.domain.model.CharacterFilter
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
interface RickAndMortyApi {
    suspend fun getAllCharacters(page: Int = 1): CharacterResponseDto
    suspend fun getCharacterById(id: Int): ApiResult<CharacterDto>
    suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int = 1
    ): ApiResult<CharacterResponseDto>
}