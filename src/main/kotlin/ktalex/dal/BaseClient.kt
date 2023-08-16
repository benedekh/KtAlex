package ktalex.dal

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

abstract class BaseClient<out T>(protected val openAlexBaseUrl: String = "https://api.openalex.org") : AutoCloseable {

    protected abstract val baseUrl: String
    abstract fun getRandom(): T

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

    protected inline fun <reified T> getItem(url: String): T? {
        var result: T? = null
        runBlocking {
            val response = client.get(url)
            if (response.status == HttpStatusCode.OK) {
                result = response.body()
            }
        }
        return result
    }

    override fun close() {
        client.close()
    }
}