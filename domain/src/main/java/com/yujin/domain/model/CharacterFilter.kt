package com.yujin.domain.model

data class CharacterFilter(
    val name: String? = null,
    val status: String? = null, // alive, dead, unknown
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null // female, male, genderless, unknown
)

