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
        if (results.isNullOrEmpty()) {
            return null
        }

        // set next page parameters
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = paginationSettings.perPage)
            } ?: queryBuilder.pagination(perPage = paginationSettings.perPage, cursor = meta?.nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = meta?.nextCursor ?: "*")

        // get the next page
        return url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)
    }

    override fun iterator(): Iterator<PageableQueryResponse<T>> =
        PageableQueryResponseIterator(
            lastResult = QueryResponse(meta, results, groupBy),
            url = url,
            nextCursor = meta?.nextCursor,
            queryBuilder = queryBuilder.copy(),
            client = client,
        )
}

class PageableQueryResponseIterator<T>(
    private var lastResult: QueryResponse<T>,
    private val url: String? = null,
    private var nextCursor: String? = null,
    private val queryBuilder: QueryBuilder,
    private val client: BaseEntityClient<T>,
) : Iterator<PageableQueryResponse<T>> {

    private var peekedResponse: PageableQueryResponse<T>? = null

    override fun hasNext(): Boolean {
        // peek into the next page
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = paginationSettings.perPage)
            } ?: queryBuilder.pagination(perPage = paginationSettings.perPage, cursor = nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = nextCursor ?: "*")

        peekedResponse = url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)
        return peekedResponse?.results?.isNotEmpty() ?: false
    }

    override fun next(): PageableQueryResponse<T> {
        return peekedResponse?.let {
            lastResult = QueryResponse(it.meta, it.results, it.groupBy)
            nextCursor = it.meta?.nextCursor
            peekedResponse = null
            it
        } ?: throw NoSuchElementException()
    }
}
