package ktalex.model

data class QueryResults<T>(
    val meta: MetaInfo,
    val results: List<T>,
    val groupBy: List<String>
)

data class MetaInfo(
    val count: Int,
    val dbResponseTimeMs: Int,
    val page: Int?,
    val perPage: Int,
    val nextCursor: String?
)

data class GroupByMetaInfo(
    val count: Int,
    val key: String,
    val keyDisplayName: String
)