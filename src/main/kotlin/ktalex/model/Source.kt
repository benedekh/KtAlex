package ktalex.model

import ktalex.utils.DateUtil
import ktalex.utils.EnumUtil
import java.time.LocalDate
import java.time.LocalDateTime

abstract class BaseSource(
    open val displayName: String,
    open val hostOrganization: String,
    open val hostOrganizationLineage: List<String>,
    open val hostOrganizationName: String,
    open val id: String,
    open val isInDoaj: Boolean,
    open val isOa: Boolean,
    open val issn: List<String>,
    open val issnl: String,
    open val type: String
) {
    val typeEnum: SourceType?
        get() = EnumUtil.valueOfOrNull<SourceType>(type)
}

data class DehydratedSource(
    override val displayName: String,
    override val hostOrganization: String,
    override val hostOrganizationLineage: List<String>,
    override val hostOrganizationName: String,
    override val id: String,
    override val isInDoaj: Boolean,
    override val isOa: Boolean,
    override val issn: List<String>,
    override val issnl: String,
    override val type: String
) : BaseSource(
    displayName,
    hostOrganization,
    hostOrganizationLineage,
    hostOrganizationName,
    id,
    isInDoaj,
    isOa,
    issn,
    issnl,
    type
)

data class Source(
    val abbreviatedTitle: String,
    val alternateTitles: List<String>,
    val apcPrices: List<Price>,
    val apcUsd: Int,
    val citedByCount: Int,
    val countryCode: String,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
    override val displayName: String,
    val homepageUrl: String,
    override val hostOrganization: String,
    override val hostOrganizationLineage: List<String>,
    override val hostOrganizationName: String,
    override val id: String,
    val ids: SourceIds,
    override val isInDoaj: Boolean,
    override val isOa: Boolean,
    override val issn: List<String>,
    override val issnl: String,
    val societies: List<Society>,
    val summaryStats: CitationMetrics,
    override val type: String,
    val updatedDate: String,
    val worksApiUrl: String, // TODO A URL that will get you a list of all this source's Works.
    val worksCount: Int,
    val xConcepts: List<RelatedConcept>
) : BaseSource(
    displayName,
    hostOrganization,
    hostOrganizationLineage,
    hostOrganizationName,
    id,
    isInDoaj,
    isOa,
    issn,
    issnl,
    type
) {
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

enum class SourceType {
    JOURNAL, BOOK_SERIES, CONFERENCE, REPOSITORY, EBOOK_PLATFORM
}

data class SourceIds(
    val fatcat: String,
    val issn: String,
    val issnl: String,
    val mag: String,
    val openalex: String,
    val wikidata: String
)