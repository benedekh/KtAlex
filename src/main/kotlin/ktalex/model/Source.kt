package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorksClient
import ktalex.dal.query.PageableQueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedEnum
import ktalex.model.serialization.SerializedId
import ktalex.model.serialization.SourceTypeSerializer

interface BaseSource {
    val displayName: String?
    val hostOrganization: String?
    val hostOrganizationLineage: List<String>?
    val hostOrganizationLineageNames: List<String>?
    val hostOrganizationName: String?
    val id: SerializedId?
    val isInDoaj: Boolean?
    val isOa: Boolean?
    val issn: List<String>?
    val issnL: String?
    val type: SerializedEnum<SourceType>?
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
    @Serializable(with = SourceTypeSerializer::class)
    override val type: SerializedEnum<SourceType>?,
) : BaseSource

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
    @Serializable(with = SourceTypeSerializer::class)
    override val type: SerializedEnum<SourceType>?,
    val updatedDate: SerializedDateTime?,
    val worksApiUrl: String?,
    val worksCount: Int?,
    val xConcepts: List<RelatedConcept>?,
) : BaseSource {
    fun resolveWorks(): PageableQueryResponse<Work>? = worksApiUrl?.let { WorksClient().getEntities(it) }
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
    val wikidata: SerializedId?,
)
