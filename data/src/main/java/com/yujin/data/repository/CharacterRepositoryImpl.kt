package com.yujin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yujin.core.model.ApiResult
import com.yujin.core.model.map
import com.yujin.data.api.RickAndMortyApi
import com.yujin.data.mapper.toDomain
import com.yujin.data.paging.CharacterPagingSource
import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterFilter
import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    override fun getAllCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 3 // 3개 남으면 추가로드
            ),
            pagingSourceFactory = { CharacterPagingSource(api) } // Paging은 내부적으로 데이터가 갱신될 떄마다 PagingSource를 새로 생성해서 사용해서 이를 위한 매번 인스턴스를 새로 생성할 람다가 필요함
        ).flow.flowOn(Dispatchers.IO)  //데이터 요청은 IO
    }

    override suspend fun getCharacterById(id: Int): ApiResult<Character> {
        return api.getCharacterById(id).map { it.toDomain() }
    }

    override suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int
    ): ApiResult<CharacterResponse> {
        return api.searchCharacters(filter, page).map { it.toDomain() }
    }


}

