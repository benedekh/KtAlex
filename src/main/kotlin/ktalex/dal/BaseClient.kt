package ktalex.dal

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

abstract class BaseClient : AutoCloseable {
    companion object {
        const val OPENALEX_BASE_URL = "https://api.openalex.org"
    }

    protected abstract val baseUrl: String

    @OptIn(ExperimentalSerializationApi::class)
    protected val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
                // ignoreUnknownKeys = true // TODO enable in production
                namingStrategy = JsonNamingStrategy.SnakeCase
            })
        }
    }

    protected suspend inline fun <reified T> getItem(url: String): T? {
        println(url)
        val response = client.get(url)
        return if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            null
        }
    }

    override fun close() {
        client.close()
    }
}