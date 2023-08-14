package ktalex.model

import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

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
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

data class FunderIds(
    val crossref: String,
    val doi: String,
    val openalex: String,
    val ror: String,
    val wikidata: String
)

