package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.SourcesClient
import ktalex.dal.query.QueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

@Serializable
data class Publisher(
    val alternateTitles: List<String>?,
    val citedByCount: Int?,
    val countryCodes: List<String>?,
    val countsByYear: List<CountsByYear>?,
    val createdDate: SerializedDate?,
    val displayName: String?,
    val hierarchyLevel: Int?,
    val homepageUrl: String?,
    val id: SerializedId?,
    val ids: PublisherIds?,
    val imageThumbnailUrl: String?,
    val imageUrl: String?,
    val lineage: List<String>?,
    val parentPublisher: String?,
    val relevanceScore: Float?,
    val roles: List<Role>?,
    val sourcesApiUrl: String?,
    val summaryStats: CitationMetrics?,
    val updatedDate: SerializedDateTime?,
    val worksCount: Int?
) {
    fun resolveSources(): QueryResponse<Source>? = sourcesApiUrl?.let { SourcesClient().getEntities(it) }
}

@Serializable
data class PublisherIds(
    val openalex: SerializedId?,
    val ror: SerializedId?,
    val wikidata: SerializedId?
)