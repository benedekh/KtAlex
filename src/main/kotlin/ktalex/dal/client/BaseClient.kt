package ktalex.dal.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import ktalex.dal.autocomplete.AutocompleteResponse
import ktalex.dal.client.request.toExtendedString
import ktalex.dal.error.ErrorResponse
import ktalex.dal.error.OpenAlexException
import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.utils.extractFirstMatch
import mu.KotlinLogging

abstract class BaseClient<out T>(protected val mailTo: String? = null) : AutoCloseable {

    companion object {
        protected val LOGGER = KotlinLogging.logger {}
    }

    @OptIn(ExperimentalSerializationApi::class)
    protected val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    // ignoreUnknownKeys = true // TODO enable in production
                    namingStrategy = JsonNamingStrategy.SnakeCase
                },
            )
        }
    }

    protected inline fun <reified T> getEntity(url: String): T? {
        var result: T? = null
        runBlocking {
            val requestBuilder = HttpRequestBuilder().apply {
                url(url)
                method = HttpMethod.Get
                mailTo?.let { headers.append(HttpHeaders.UserAgent, "mailto:$mailTo") }
            }
            LOGGER.debug { requestBuilder.build().toExtendedString() }

            val response = client.get(requestBuilder)

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

abstract class BaseEntityClient<T> : BaseClient<T> {

    constructor(openAlexBaseUrl: String? = null, mailTo: String? = null) : super(mailTo) {
        this.openAlexBaseUrl = openAlexBaseUrl ?: "https://api.openalex.org"
    }

    private val openAlexBaseUrl: String

    protected abstract val entityType: String

    private val autocompleteBaseUrl: String
        get() = "$openAlexBaseUrl/autocomplete/$entityType"

    protected val baseUrl: String
        get() = "$openAlexBaseUrl/$entityType"

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
    fun autocomplete(term: String, queryBuilder: QueryBuilder? = null): AutocompleteResponse =
        getEntity("$autocompleteBaseUrl?q=$term${queryBuilder?.let { "&${it.build().removePrefix("?")}" } ?: ""}")!!

    protected abstract fun getEntityWithExactType(url: String): QueryResponse<T>

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

        val response = getEntityWithExactType(preparedUrl)
        return PageableQueryResponse(
            meta = response.meta,
            results = response.results,
            groupBy = response.groupBy,
            url = url,
            queryBuilder = preparedQueryBuilder.copy(),
            client = this,
        )
    }
}
