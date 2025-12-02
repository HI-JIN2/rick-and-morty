package com.yujin.presentation.characterdetail


/**
 * UI State that represents CharacterDetailScreen
 **/
class CharacterDetailState

/**
 * CharacterDetail Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class CharacterDetailActions(
    val onClick: () -> Unit = {}
)


