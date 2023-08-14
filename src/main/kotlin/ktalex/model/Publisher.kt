package ktalex.model

import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

data class Publisher(
    val alternateTitles: List<String>,
    val citedByCount: Int,
    val countryCodes: List<String>,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
    val displayName: String,
    val hierarchyLevel: Int,
    val id: String,
    val ids: PublisherIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val lineage: List<String>,
    val parentPublisher: String,
    val roles: List<Role>,
    val sourcesApiUrl: String, // TODO An URL that will get you a list of all the sources published by this publisher.
    val summaryStats: CitationMetrics,
    val updatedDate: String,
    val worksCount: Int
) {
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

data class PublisherIds(
    val openalex: String,
    val ror: String,
    val wikidata: String
)