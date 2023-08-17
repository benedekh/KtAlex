package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorksClient
import ktalex.dal.query.QueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedEnum
import ktalex.model.serialization.SerializedId

@Serializable
abstract class BaseSource {
    abstract val displayName: String?
    abstract val hostOrganization: String?
    abstract val hostOrganizationLineage: List<String>?
    abstract val hostOrganizationLineageNames: List<String>?
    abstract val hostOrganizationName: String?
    abstract val id: SerializedId?
    abstract val isInDoaj: Boolean?
    abstract val isOa: Boolean?
    abstract val issn: List<String>?
    abstract val issnL: String?
    abstract val type: SerializedEnum<SourceType>?
}

@Serializable
data class DehydratedSource(
    override val displayName: String?,
    override val hostOrganization: String?,
    override val hostOrganizationLineage: List<String>?,
    override val hostOrganizationLineageNames: List<String>?,
    override val hostOrganizationName: String?,
    override val id: SerializedId?,
    override val isInDoaj: Boolean?,
    override val isOa: Boolean?,
    override val issn: List<String>?,
    override val issnL: String?,
    override val type: SerializedEnum<SourceType>?
) : BaseSource()

@Serializable
data class Source(
    val abbreviatedTitle: String?,
    val alternateTitles: List<String>?,
    val apcPrices: List<Price>?,
    val apcUsd: Int?,
    val citedByCount: Int?,
    val countryCode: String?,
    val countsByYear: List<CountsByYear>?,
    val createdDate: SerializedDate?,
    override val displayName: String?,
    val homepageUrl: String?,
    override val hostOrganization: String?,
    override val hostOrganizationLineage: List<String>?,
    override val hostOrganizationLineageNames: List<String>?,
    override val hostOrganizationName: String?,
    override val id: SerializedId?,
    val ids: SourceIds?,
    override val isInDoaj: Boolean?,
    override val isOa: Boolean?,
    override val issn: List<String>?,
    override val issnL: String?,
    val relevanceScore: Float?,
    val societies: List<Society>?,
    val summaryStats: CitationMetrics?,
    override val type: SerializedEnum<SourceType>?,
    val updatedDate: SerializedDateTime?,
    val worksApiUrl: String?,
    val worksCount: Int?,
    val xConcepts: List<RelatedConcept>?
) : BaseSource() {
    // TODO testme
    fun resolveWorks(): QueryResponse<Work>? = worksApiUrl?.let { WorksClient().getEntities(it) }
}

enum class SourceType {
    JOURNAL, BOOK_SERIES, CONFERENCE, REPOSITORY, EBOOK_PLATFORM
}

@Serializable
data class SourceIds(
    val fatcat: String?,
    val issn: List<String>?,
    val issnL: String?,
    val mag: String?,
    val openalex: SerializedId?,
    val wikidata: SerializedId?
)