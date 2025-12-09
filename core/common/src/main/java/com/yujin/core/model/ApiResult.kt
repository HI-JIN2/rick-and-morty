package com.yujin.core.model

import java.io.IOException

sealed interface ApiResult<out T> {
    // API가 성공했을 때
    data class Success<T>(val data: T) : ApiResult<T>

    // 서버에서 에러 반환
    data class Failure(
        val responseCode: Int,
        val message: String?
    ) : ApiResult<Nothing>

    // 소켓 연결 끊김, 타임아웃 등 네트워크 예외인 IOException 처리
    data class NetworkError(
        val exception: IOException
    ) : ApiResult<Nothing>

    // IOException을 제외한 예외 처리
    data class UnknownError(
        val exception: Throwable
    ) : ApiResult<Nothing>
}

// ApiResult가 Success일 때 데이터를 변환
fun <T, R> ApiResult<T>.map(transform: (T) -> R): ApiResult<R> = when (this) {
    is ApiResult.Success<T> -> ApiResult.Success(transform(data))
    is ApiResult.Failure -> ApiResult.Failure(responseCode, message)
    is ApiResult.NetworkError -> ApiResult.NetworkError(exception)
    is ApiResult.UnknownError -> ApiResult.UnknownError(exception)
}

