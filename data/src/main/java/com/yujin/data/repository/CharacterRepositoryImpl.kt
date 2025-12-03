package com.yujin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yujin.data.api.RickAndMortyApi
import com.yujin.data.mapper.toDomain
import com.yujin.data.paging.CharacterPagingSource
import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {

    override fun getAllCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(api) }
        ).flow
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

