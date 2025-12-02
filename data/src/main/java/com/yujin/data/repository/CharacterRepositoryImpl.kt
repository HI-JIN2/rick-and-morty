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
        return try {
            val response = api.getAllCharacters(page)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCharacterById(id: Int): Result<Character> {
        return try {
            val response = api.getCharacterById(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int
    ): Result<CharacterResponse> {
        return try {
            val response = api.searchCharacters(filter, page)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

