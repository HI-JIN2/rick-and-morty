package com.yujin.data.mapper

import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.data.dto.LocationDto
import com.yujin.data.dto.PageInfoDto
import com.yujin.domain.model.Character
import com.yujin.domain.model.CharacterResponse
import com.yujin.domain.model.Location
import com.yujin.domain.model.PageInfo

fun CharacterDto.toDomain(): Character {
    return Character(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        origin = origin.toDomain(),
        location = location.toDomain(),
        image = image,
        episode = episode,
        url = url,
        created = created
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        name = name,
        url = url
    )
}

fun CharacterResponseDto.toDomain(): CharacterResponse {
    return CharacterResponse(
        info = info.toDomain(),
        results = results.map { it.toDomain() }
    )
}

fun PageInfoDto.toDomain(): PageInfo {
    return PageInfo(
        count = count,
        pages = pages,
        next = next,
        prev = prev
    )
}

