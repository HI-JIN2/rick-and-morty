package com.yujin.presentation.common

import com.yujin.core.model.ApiResult

/**
 * ApiResult를 UiState로 변환하는 확장 함수들
 */
fun <T, R> ApiResult<T>.toUiState(
    onSuccess: (T) -> R
): UiState<R> = when (this) {
    is ApiResult.Success -> UiState.Success(onSuccess(data))
    is ApiResult.Failure -> UiState.Error(
        throwable = Throwable(
            "Server error: ${responseCode} - ${message ?: "Unknown error"}"
        )
    )

    is ApiResult.NetworkError -> UiState.Error(throwable = exception)
    is ApiResult.UnknownError -> UiState.Error(throwable = exception)
}
