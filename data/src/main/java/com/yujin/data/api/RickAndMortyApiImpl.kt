package com.yujin.data.api

import com.yujin.core.model.ApiResult
import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.data.dto.ErrorDto
import com.yujin.domain.model.CharacterFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.JsonConvertException
import kotlinx.serialization.json.Json
import java.io.IOException

private val json = Json { ignoreUnknownKeys = true }

class RickAndMortyApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : RickAndMortyApi {

    override suspend fun getAllCharacters(page: Int): CharacterResponseDto {
        return client.get("$baseUrl/character") {
            parameter("page", page)
        }.body()
    }

    override suspend fun getCharacterById(id: Int): ApiResult<CharacterDto> =
        safeCall(
            call = {
                client.get("$baseUrl/character/$id")
            },
            onSuccess = { response ->
                response.body<CharacterDto>()
            }
        )


    override suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int
    ): ApiResult<CharacterResponseDto> =
        safeCall(
            call = {
                client.get("$baseUrl/character") {
                    parameter("page", page)
                    filter.name?.let { parameter("name", it) }
                    filter.status?.let { parameter("status", it) }
                    filter.species?.let { parameter("species", it) }
                    filter.type?.let { parameter("type", it) }
                    filter.gender?.let { parameter("gender", it) }
                }
            },
            onSuccess = { response ->
                response.body<CharacterResponseDto>()
            }
        )
}


private suspend inline fun <T> safeCall(
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
        val code = e.response.status.value

        ApiResult.Failure(code, e.message ?: "Client error")

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
