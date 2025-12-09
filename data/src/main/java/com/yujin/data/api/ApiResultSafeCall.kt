package com.yujin.data.api

import com.yujin.core.model.ApiResult
import com.yujin.data.dto.ErrorDto
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import java.io.IOException

val json = Json { ignoreUnknownKeys = true }

@OptIn(InternalSerializationApi::class)
suspend inline fun <T> safeCall(
    crossinline call: suspend () -> HttpResponse,
    crossinline onSuccess: suspend (HttpResponse) -> T
): ApiResult<T> {
    return try {
        val response = call()
        val status = response.status.value

        // 404는 body() 형식이 달라 파싱이 실패하므로 직접 처리
        if (status == 404) {
            val raw = response.bodyAsText()

            val errorDto = try {
                json.decodeFromString<ErrorDto>(raw)
            } catch (_: Exception) {
                null
            }

            return ApiResult.Failure(
                responseCode = 404,
                message = errorDto?.error
            )
        }

        // 정상 응답 → body() 파싱
        ApiResult.Success(onSuccess(response))

    } catch (e: ClientRequestException) {
        ApiResult.Failure(
            responseCode = e.response.status.value,
            message = e.message ?: "Client error"
        )

    } catch (e: ServerResponseException) {
        ApiResult.Failure(
            responseCode = e.response.status.value,
            message = e.message ?: "Server error"
        )

    } catch (e: IOException) {
        ApiResult.NetworkError(e)

    } catch (e: JsonConvertException) {
        ApiResult.UnknownError(e)

    } catch (e: Throwable) {
        ApiResult.UnknownError(e)
    }
}
