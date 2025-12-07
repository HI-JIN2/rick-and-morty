package com.yujin.data.api

import com.yujin.core.model.ApiResult
import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.domain.model.CharacterFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.io.IOException

class RickAndMortyApiImpl(
    private val client: HttpClient,
    private val baseUrl: String
) : RickAndMortyApi {

    override suspend fun getAllCharacters(page: Int): CharacterResponseDto {
        return client.get("$baseUrl/character") {
            parameter("page", page)
        }.body()
    }

    override suspend fun getCharacterById(id: Int): ApiResult<CharacterDto> {
        return try {
            val response = client.get("$baseUrl/character/$id").body<CharacterDto>()
            ApiResult.Success(response)
        } catch (e: ClientRequestException) {
            // 4xx 에러
            ApiResult.Failure(
                responseCode = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
            // 5xx 에러
            ApiResult.Failure(
                responseCode = e.response.status.value,
                message = e.message
            )
        } catch (e: IOException) {
            // 네트워크 에러
            ApiResult.NetworkError(e)
        } catch (e: Throwable) {
            // 기타 예외
            ApiResult.UnknownError(e)
        }
    }

    override suspend fun searchCharacters(
        filter: CharacterFilter,
        page: Int
    ): ApiResult<CharacterResponseDto> {
        return try {
            val response = client.get("$baseUrl/character") {
                parameter("page", page)
                filter.name?.let { parameter("name", it) }
                filter.status?.let { parameter("status", it) }
                filter.species?.let { parameter("species", it) }
                filter.type?.let { parameter("type", it) }
                filter.gender?.let { parameter("gender", it) }
            }.body<CharacterResponseDto>()
            ApiResult.Success(response)
        } catch (e: ClientRequestException) {
            // 4xx 에러
            ApiResult.Failure(
                responseCode = e.response.status.value,
                message = e.message
            )
        } catch (e: ServerResponseException) {
            // 5xx 에러
            ApiResult.Failure(
                responseCode = e.response.status.value,
                message = e.message
            )
        } catch (e: IOException) {
            // 네트워크 에러
            ApiResult.NetworkError(e)
        } catch (e: Throwable) {
            // 기타 예외
            ApiResult.UnknownError(e)
        }
    }
}

