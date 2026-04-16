package com.example.myapplication.networking

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val BASE_URL = "https://rma.finlab.rs/movies/"

val networkingModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    }
                )
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    single {
        Ktorfit.Builder()
            .httpClient(get<HttpClient>())
            .baseUrl(BASE_URL)
            .build()
            .create<MovieApi>()
    }
}