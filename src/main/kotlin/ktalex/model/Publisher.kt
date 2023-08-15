package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class Publisher(
    val alternateTitles: List<String>,
    val citedByCount: Int,
    val countryCodes: List<String>,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
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
    val updatedDate: String,
    val worksCount: Int
) {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

@Serializable
data class PublisherIds(
    val openalex: String,
    val ror: String,
    val wikidata: String
)