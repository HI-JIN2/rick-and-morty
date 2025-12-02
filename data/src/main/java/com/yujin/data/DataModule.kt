package com.yujin.data

import com.yujin.data.api.RickAndMortyApi
import com.yujin.data.api.RickAndMortyApiImpl
import com.yujin.data.repository.CharacterRepositoryImpl
import com.yujin.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideKtorHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(DefaultRequest) {
                contentType(Json)
            }
        }
    }

    @Singleton
    @Provides
    fun provideRickAndMortyApi(
        client: HttpClient
    ): RickAndMortyApi {
        return RickAndMortyApiImpl(client, HttpRoutes.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideCharacterRepository(
        api: RickAndMortyApi
    ): CharacterRepository {
        return CharacterRepositoryImpl(api)
    }
}
