package ktalex.dal.client

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
import ktalex.dal.autocomplete.AutocompleteResponse
import ktalex.dal.error.ErrorResponse
import ktalex.dal.error.OpenAlexException
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse

abstract class BaseClient<T>(protected val openAlexBaseUrl: String = "https://api.openalex.org") : AutoCloseable {

    protected val baseUrl: String = "$openAlexBaseUrl/$entityType"

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

    protected abstract val entityType: String

    abstract fun getRandom(queryBuilder: QueryBuilder? = null): T
    abstract fun getEntities(queryBuilder: QueryBuilder? = null): QueryResponse<T>

    /**
     * Completes the given term with possible continuations to get a list of entities. For details see the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities).
     *
     * @param term the term to complete
     * @param queryBuilder to use "filter" and "search" queries in autocomplete
     */
    fun autocomplete(term: String, queryBuilder: QueryBuilder? = null): AutocompleteResponse = getEntity(
        "$openAlexBaseUrl/autocomplete/$entityType?q=$term${
            if (queryBuilder != null) {
                "&${queryBuilder.build().removePrefix("?")}"
            } else {
                ""
            }
        }"
    )!!

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