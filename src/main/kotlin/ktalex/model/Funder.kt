package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class Funder(
    val alternateTitles: List<String>,
    val citedByCount: Int,
    val countryCode: String,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
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
    val updatedDate: String,
    val worksCount: Int
) {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

@Serializable
data class FunderIds(
    val crossref: String,
    val doi: String,
    val openalex: String,
    val ror: String,
    val wikidata: String
)

