package com.yujin.presentation.characterlist.model

import com.yujin.domain.model.Character

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String,
)


fun Character.toUiModel(): CharacterUiModel {
    return CharacterUiModel(
        id = id,
        name = name,
        status = status,
        gender = gender,
        image = image,
    )
}


