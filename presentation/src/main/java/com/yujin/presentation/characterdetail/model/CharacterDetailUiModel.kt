package com.yujin.presentation.characterdetail.model

import com.yujin.domain.model.Character

data class CharacterDetailUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val origin: String,
    val location: String,
)

fun Character.toDetailUiModel(): CharacterDetailUiModel {
    return CharacterDetailUiModel(
        id = id,
        name = name,
        status = status,
        species = species,
        gender = gender,
        image = image,
        origin = origin.name,
        location = location.name,
    )
}
