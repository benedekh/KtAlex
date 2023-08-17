package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class QueryResults<T>(
    val meta: MetaInfo?,
    val results: List<T>?,
    val groupBy: List<GroupByMetaInfo>?
)

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