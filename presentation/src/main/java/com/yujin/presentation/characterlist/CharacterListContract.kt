package com.yujin.presentation.characterlist


/**
 * UI State that represents CharacterListScreen
 **/
class CharacterListState

/**
 * CharacterList Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterListActions(
    val onClick: () -> Unit = {}
)


