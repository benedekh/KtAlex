package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

@Serializable
data class Funder(
    val alternateTitles: List<String>,
    val citedByCount: Int,
    val countryCode: String,
    val countsByYear: List<CountsByYear>,
    val createdDate: SerializedDate,
    val description: String,
    val displayName: String,
    val grantsCount: Int,
    val homepageUrl: String,
    val id: SerializedId,
    val ids: FunderIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val roles: List<Role>,
    val summaryStats: CitationMetrics,
    val updatedDate: SerializedDateTime,
    val worksCount: Int
)

@Serializable
data class FunderIds(
    val crossref: String,
    val doi: String,
    val openalex: SerializedId,
    val ror: SerializedId,
    val wikidata: SerializedId
)

