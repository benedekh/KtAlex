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
import ktalex.dal.error.ErrorResponse
import ktalex.dal.error.OpenAlexException
import ktalex.dal.query.QueryBuilder
import ktalex.model.QueryResults

abstract class BaseClient<T>(protected val openAlexBaseUrl: String = "https://api.openalex.org") : AutoCloseable {

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

    abstract fun getRandom(queryBuilder: QueryBuilder? = null): T
    abstract fun getEntities(queryBuilder: QueryBuilder? = null): QueryResults<T>

    protected inline fun <reified T> getEntity(url: String): T? {
        var result: T? = null
        runBlocking {
            val response = client.get(url)
            if (response.status == HttpStatusCode.OK) {
                result = response.body()
            } else if (response.status == HttpStatusCode.Forbidden) {
                val error: ErrorResponse = response.body()
                error.message?.let { throw OpenAlexException(it) }
            }
        }
        return result
    }

    override fun close() {
        client.close()
    }
}