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
import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.utils.extractFirstMatch

abstract class BaseClient<T> : AutoCloseable {

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


    protected inline fun <reified T> getEntity(url: String): T? {
        println(url)
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

abstract class BaseEntityClient<T>(protected val openAlexBaseUrl: String = "https://api.openalex.org") :
    BaseClient<T>() {
    protected val baseUrl: String
        get() = "$openAlexBaseUrl/$entityType"

    protected abstract val entityType: String

    abstract fun getRandom(queryBuilder: QueryBuilder? = null): T

    abstract fun getEntities(url: String): PageableQueryResponse<T>

    abstract fun getEntities(queryBuilder: QueryBuilder? = null): PageableQueryResponse<T>

    abstract fun getEntities(url: String, queryBuilder: QueryBuilder? = null): PageableQueryResponse<T>

    /**
     * Completes the given term with possible continuations to get a list of entities. For details see the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities).
     *
     * @param term the term to complete
     * @param queryBuilder to use "filter" and "search" queries in autocomplete
     */
    fun autocomplete(term: String, queryBuilder: QueryBuilder? = null): AutocompleteResponse = getEntity(
        "$openAlexBaseUrl/autocomplete/$entityType?q=$term${
            queryBuilder?.let { "&${it.build().removePrefix("?")}" } ?: ""
        }"
    )!!

    protected fun getEntitiesInternal(url: String): PageableQueryResponse<T> {
        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)
        val (perPage, urlAfterPerPageRemoved) = urlAfterPageRemoved.extractFirstMatch("per_page", true)
        val (cursor, urlAfterCursorRemoved) = urlAfterPerPageRemoved.extractFirstMatch("cursor", true)

        val queryBuilder = QueryBuilder().pagination(page?.toInt(), perPage?.toInt(), cursor)
        return getEntities(urlAfterCursorRemoved, queryBuilder)
    }

    protected fun getEntitiesInternal(queryBuilder: QueryBuilder? = null): PageableQueryResponse<T> =
        getEntities(baseUrl, queryBuilder)

    protected fun getEntitiesInternal(url: String, queryBuilder: QueryBuilder? = null): PageableQueryResponse<T> {
        val preparedQueryBuilder = (queryBuilder ?: QueryBuilder()).withDefaultPagination()

        val queryParams = preparedQueryBuilder.build()
        val preparedUrl = if (url.contains("?") || url.contains("&")) {
            // the URL already contains query params
            "$url&${queryParams.removePrefix("?")}"
        } else {
            "$url$queryParams"
        }
        val response: QueryResponse<T> = getEntity(preparedUrl)!!
        return PageableQueryResponse(
            meta = response.meta,
            results = response.results,
            groupBy = response.groupBy,
            url = url,
            queryBuilder = preparedQueryBuilder.copy(),
            client = this
        )
    }

}