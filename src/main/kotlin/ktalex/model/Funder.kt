package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime

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
    val id: String,
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
    val openalex: String,
    val ror: String,
    val wikidata: String
)

