package ktalex.dal.query

import kotlinx.serialization.Serializable
import ktalex.dal.client.BaseEntityClient

interface BaseQueryResponse<T> {
    val meta: MetaInfo?
    val results: List<T>?
    val groupBy: List<GroupByMetaInfo>?
}

@Serializable
data class QueryResponse<T>(
    override val meta: MetaInfo?,
    override val results: List<T>?,
    override val groupBy: List<GroupByMetaInfo>?,
) : BaseQueryResponse<T>

@Serializable
data class MetaInfo(
    val count: Int?,
    val dbResponseTimeMs: Int?,
    val page: Int?,
    val perPage: Int?,
    val nextCursor: String?,
)

@Serializable
data class GroupByMetaInfo(
    val count: Int?,
    val key: String?,
    val keyDisplayName: String?,
)

data class PageableQueryResponse<T>(
    override val meta: MetaInfo?,
    override val results: List<T>?,
    override val groupBy: List<GroupByMetaInfo>?,
    private val url: String? = null,
    private val queryBuilder: QueryBuilder,
    private val client: BaseEntityClient<T>,
) : BaseQueryResponse<T>, Iterable<PageableQueryResponse<T>> {

    fun nextPage(): PageableQueryResponse<T>? {
        // set next page parameters
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = paginationSettings.perPage)
            } ?: queryBuilder.pagination(perPage = paginationSettings.perPage, cursor = meta?.nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = meta?.nextCursor ?: "*")

        // peek into the next page
        val peekedPage = url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)
        return if (peekedPage.results.isNullOrEmpty()) {
            null
        } else {
            peekedPage
        }
    }
    
    override fun iterator(): Iterator<PageableQueryResponse<T>> =
        PageableQueryResponseIterator(
            lastResult = this,
            url = url,
            nextCursor = meta?.nextCursor,
            queryBuilder = queryBuilder.copy(),
            client = client,
        )
}

class PageableQueryResponseIterator<T>(
    private var lastResult: PageableQueryResponse<T>?,
    private val url: String? = null,
    private var nextCursor: String? = null,
    private val queryBuilder: QueryBuilder,
    private val client: BaseEntityClient<T>,
) : Iterator<PageableQueryResponse<T>> {

    private var hasReturnedInitialResults: Boolean = false

    @Suppress("detekt:ReturnCount")
    override fun hasNext(): Boolean {
        if (!hasReturnedInitialResults) {
            return lastResult != null
        }
        if (lastResult == null) {
            return false
        }

        // peek into the next page
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = paginationSettings.perPage)
            } ?: queryBuilder.pagination(perPage = paginationSettings.perPage, cursor = nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = nextCursor ?: "*")

        lastResult = url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)
        val hasNext = lastResult?.results?.isNotEmpty() ?: false
        if (!hasNext) {
            lastResult = null
        }
        return hasNext
    }

    override fun next(): PageableQueryResponse<T> {
        return lastResult?.apply {
            nextCursor = this.meta?.nextCursor
            hasReturnedInitialResults = true
        } ?: throw NoSuchElementException()
    }
}
