package com.yujin.presentation.characterlist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.yujin.domain.model.Character
import com.yujin.domain.repository.CharacterRepository

class CharacterPagingSource(
    private val repository: CharacterRepository
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1

            repository.getAllCharacters(page).fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.results,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.info.next == null) null else page + 1
                    )
                },
                onFailure = { error ->
                    LoadResult.Error(error)
                }
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

