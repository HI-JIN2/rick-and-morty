package com.yujin.data.dto

import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.model.PageInfo
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@OptIn(InternalSerializationApi::class)
@Serializable
data class CharacterResponseDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>
) {
    @Serializable
    data class PageInfoDto(
        val count: Int,
        val pages: Int,
        val next: String? = null,
        val prev: String? = null
    )
}

@OptIn(InternalSerializationApi::class)
fun CharacterResponseDto.toDomain(): CharacterResponse {
    return CharacterResponse(
        info = info.toDomain(),
        results = results.map { it.toDomain() }
    )
}


@OptIn(InternalSerializationApi::class)
fun CharacterResponseDto.PageInfoDto.toDomain(): PageInfo {
    return PageInfo(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

