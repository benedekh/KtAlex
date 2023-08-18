package ktalex.dal.query

import kotlinx.serialization.Serializable
import ktalex.dal.client.BaseEntityClient

@Serializable
abstract class BaseQueryResponse<T> {
    abstract val meta: MetaInfo?
    abstract val results: List<T>?
    abstract val groupBy: List<GroupByMetaInfo>?
}

@Serializable
data class QueryResponse<T>(
    override val meta: MetaInfo?,
    override val results: List<T>?,
    override val groupBy: List<GroupByMetaInfo>?
) : BaseQueryResponse<T>()

@Serializable
data class MetaInfo(
    val count: Int?,
    val dbResponseTimeMs: Int?,
    val page: Int?,
    val perPage: Int?,
    val nextCursor: String?
)

@Serializable
data class GroupByMetaInfo(
    val count: Int?,
    val key: String?,
    val keyDisplayName: String?
)

class PageableQueryResponse<T>(
    override val meta: MetaInfo?,
    override val results: List<T>?,
    override val groupBy: List<GroupByMetaInfo>?,
    private val url: String? = null,
    private val queryBuilder: QueryBuilder,
    private val client: BaseEntityClient<T>
) : BaseQueryResponse<T>(), Iterable<PageableQueryResponse<T>> {

    fun nextPage(): PageableQueryResponse<T> {
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
        PageableQueryResponseIterator(url, meta?.nextCursor, queryBuilder.copy(), client)
}

class PageableQueryResponseIterator<T>(
    private val url: String? = null,
    private var nextCursor: String? = null,
    private val queryBuilder: QueryBuilder,
    private val client: BaseEntityClient<T>
) : Iterator<PageableQueryResponse<T>> {

    override fun hasNext(): Boolean {
        val queryBuilder = queryBuilder.copy()

        // peek into the next page
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = 1)
            } ?: queryBuilder.pagination(perPage = 1, cursor = nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = nextCursor ?: "*")

        val response = url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)
        return response.results?.isNotEmpty() ?: false
    }

    override fun next(): PageableQueryResponse<T> {
        // set next page parameters
        queryBuilder.paginationSettings?.let { paginationSettings ->
            paginationSettings.page?.let {
                queryBuilder.pagination(page = it + 1, perPage = paginationSettings.perPage)
            } ?: queryBuilder.pagination(perPage = paginationSettings.perPage, cursor = nextCursor ?: "*")
        } ?: queryBuilder.pagination(cursor = nextCursor ?: "*")

        // get the next page
        val response = url?.let { client.getEntities(it, queryBuilder) } ?: client.getEntities(queryBuilder)

        // update cursor, the other pagination parameters will update themselves on the next iteration
        nextCursor = response.meta?.nextCursor

        return response
    }
}