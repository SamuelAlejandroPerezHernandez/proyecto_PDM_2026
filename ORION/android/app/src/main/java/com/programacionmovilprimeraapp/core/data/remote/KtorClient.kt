package com.programacionmovilprimeraapp.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient{
    private const val BASE_URL = "http://100.118.117.122:3000/"
    val client = HttpClient(OkHttp){
        install(ContentNegotiation){
            json(
                Json{
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                }
            )
        }

        defaultRequest{
            url(BASE_URL)
            header(HttpHeaders.Accept, "application/json")
        }
    }
}