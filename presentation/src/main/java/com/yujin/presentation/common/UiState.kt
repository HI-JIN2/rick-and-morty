package com.yujin.presentation.common

sealed interface UiState<out T> {
    object Init : UiState<Nothing>

    object Loading : UiState<Nothing>

    data class Success<out T>(
        val data: T,
    ) : UiState<T>

    data class Error(
        val throwable: Throwable
    ) : UiState<Nothing>
}