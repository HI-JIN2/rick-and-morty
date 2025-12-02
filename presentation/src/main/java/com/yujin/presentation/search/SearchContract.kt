package com.yujin.presentation.search


/**
 * UI State that represents SearchScreen
 **/
class SearchState

/**
 * Search Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class SearchActions(
    val onClick: () -> Unit = {}
)


