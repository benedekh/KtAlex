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
import java.util.concurrent.atomic.AtomicReference

abstract class BaseClient : AutoCloseable {

    /**
     * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
     * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
     * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
     * contact you."
     * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
     */
    constructor(openAlexBaseUrl: String? = null, mailTo: String? = null) {
        this.openAlexBaseUrl = openAlexBaseUrl ?: "https://api.openalex.org"
        this.mailTo = mailTo
    }

    protected val mailTo: String?
    protected val openAlexBaseUrl: String

    protected abstract val entityType: String

    protected val baseUrl: String
        get() = "$openAlexBaseUrl/$entityType"

    companion object {
        protected val LOGGER = KotlinLogging.logger {}
    }

    @OptIn(ExperimentalSerializationApi::class)
    protected val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    namingStrategy = JsonNamingStrategy.SnakeCase
                },
            )
        }
    }

    protected inline fun <reified T> getEntity(url: String): T {
        val reference = AtomicReference<T>()

        runBlocking {
            val requestBuilder = HttpRequestBuilder().apply {
                url(url)
                method = HttpMethod.Get
                mailTo?.let { headers.append(HttpHeaders.UserAgent, "mailto:$mailTo") }
            }
            LOGGER.debug { requestBuilder.build().toExtendedString() }

            val response = client.get(requestBuilder)

            when (response.status) {
                HttpStatusCode.OK -> {
                    reference.set(response.body())
                }

                HttpStatusCode.Forbidden -> {
                    val error: ErrorResponse = response.body()
                    throw OpenAlexException(error.message ?: "Access forbidden by OpenAlex API")
                }

                else -> {
                    throw OpenAlexException("Unexpected response status: ${response.status}")
                }
            }
        }

        return reference.get()
    }

    override fun close() {
        client.close()
    }
}

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
abstract class BaseEntityClient<T>(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseClient(openAlexBaseUrl, mailTo) {

    private val autocompleteBaseUrl: String
        get() = "$openAlexBaseUrl/autocomplete/$entityType"

    abstract fun getRandom(queryBuilder: QueryBuilder? = null): T

    abstract fun getEntitiesByUrl(url: String): PageableQueryResponse<T>

    abstract fun getEntities(queryBuilder: QueryBuilder? = null): PageableQueryResponse<T>

    abstract fun getEntities(url: String, queryBuilder: QueryBuilder? = null): PageableQueryResponse<T>

    /**
     * Completes the given term with possible continuations to get a list of entities. For details see the OpenAlex
     * documentation [here](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities).
     *
     * @param term the term to complete
     * @param queryBuilder to use "filter" and "search" queries in autocomplete
     */
    fun autocomplete(term: String, queryBuilder: QueryBuilder? = null): AutocompleteResponse =
        getEntity("$autocompleteBaseUrl?q=$term${queryBuilder?.let { "&${it.build().removePrefix("?")}" }.orEmpty()}")

    protected abstract fun getEntityByUrl(url: String): QueryResponse<T>

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

        val response = getEntityByUrl(preparedUrl)
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
