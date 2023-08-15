package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime

@Serializable
data class Publisher(
    val alternateTitles: List<String>,
    val citedByCount: Int,
    val countryCodes: List<String>,
    val countsByYear: List<CountsByYear>,
    val createdDate: SerializedDate,
    val displayName: String,
    val hierarchyLevel: Int,
    val homepageUrl: String,
    val id: String,
    val ids: PublisherIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val lineage: List<String>,
    val parentPublisher: String?,
    val roles: List<Role>,
    val sourcesApiUrl: String, // TODO An URL that will get you a list of all the sources published by this publisher.
    val summaryStats: CitationMetrics,
    val updatedDate: SerializedDateTime,
    val worksCount: Int
)

@Serializable
data class PublisherIds(
    val openalex: String,
    val ror: String,
    val wikidata: String
)