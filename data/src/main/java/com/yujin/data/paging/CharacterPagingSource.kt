package com.yujin.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yujin.data.api.RickAndMortyApi
import com.yujin.data.mapper.toDomain
import com.yujin.domain.model.Character

class CharacterPagingSource(
    private val api: RickAndMortyApi
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1

            val response = api.getAllCharacters(page)
            val characters = response.results.map { it.toDomain() }

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

