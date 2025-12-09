package com.yujin.data.api

import com.yujin.core.model.ApiResult
import com.yujin.data.dto.CharacterDto
import com.yujin.data.dto.CharacterResponseDto
import com.yujin.domain.model.CharacterFilter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

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

