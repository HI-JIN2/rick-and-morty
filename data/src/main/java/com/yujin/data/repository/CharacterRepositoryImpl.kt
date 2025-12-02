package com.yujin.data.repository

import com.yujin.data.api.RickAndMortyApi
import com.yujin.data.mapper.toDomain
import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {

    override suspend fun getAllCharacters(page: Int): Result<CharacterResponse> {
        return runCatching {
            api.getAllCharacters(page).toDomain()
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return runCatching {
            api.getCharacterById(id).toDomain()
        }
    }

    override suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int
    ): Result<CharacterResponse> {
        return runCatching {
            api.searchCharacters(filter, page).toDomain()
        }
    }
}

