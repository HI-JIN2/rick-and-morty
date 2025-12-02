package com.yujin.domain.repository

import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterResponse

interface CharacterRepository {
    suspend fun getAllCharacters(page: Int = 1): Result<CharacterResponse>
    suspend fun getCharacterById(id: Int): Result<Character>
    suspend fun searchCharacters(filter: CharacterFilter, page: Int = 1): Result<CharacterResponse>
}

