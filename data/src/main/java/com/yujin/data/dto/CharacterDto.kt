package com.yujin.data.dto

import com.yujin.domain.model.Character
import com.yujin.domain.model.Location
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationDto,
    val location: LocationDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    @Serializable
    data class LocationDto(
        val name: String,
        val url: String
    )
}

@OptIn(InternalSerializationApi::class)
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

@OptIn(InternalSerializationApi::class)
fun CharacterDto.LocationDto.toDomain(): Location {
    return Location(
        name = name,
        url = url
    )
}

