package com.yujin.presentation.characterlist

/**
 * CharacterList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterListActions(
    val onCharacterClick: (Int) -> Unit = {}
)
