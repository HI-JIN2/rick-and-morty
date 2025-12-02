package com.yujin.data.api

import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.domain.model.CharacterFilter

interface RickAndMortyApi {
    suspend fun getAllCharacters(page: Int = 1): CharacterResponseDto
    suspend fun getCharacterById(id: Int): CharacterDto
    suspend fun searchCharacters(filter: CharacterFilter, page: Int = 1): CharacterResponseDto
}