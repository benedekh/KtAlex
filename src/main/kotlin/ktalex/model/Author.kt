package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorkClient
import ktalex.dal.query.PageableQueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

interface BaseAuthor {
    val id: SerializedId?
    val displayName: String?
    val orcid: String?
}

@Serializable
data class DehydratedAuthor(
    override val id: SerializedId?,
    override val displayName: String?,
    override val orcid: String?,
) : BaseAuthor

@Serializable
data class Author(
    val citedByCount: Int?,
    val countsByYear: List<CountsByYear>?,
    val createdDate: SerializedDate?,
    override val displayName: String?,
    val displayNameAlternatives: List<String>?,
    override val id: SerializedId?,
    val ids: AuthorIds?,
    val lastKnownInstitution: DehydratedInstitution?,
    override val orcid: String?,
    val relevanceScore: Float?,
    val summaryStats: CitationMetrics?,
    val updatedDate: SerializedDateTime?,
    val worksApiUrl: String?,
    val worksCount: Int?,
    val xConcepts: List<RelatedConcept>?,
) : BaseAuthor {
    fun resolveWorks(): PageableQueryResponse<Work>? =
        worksApiUrl?.let { url -> WorkClient().use { it.getEntitiesByUrl(url) } }
}

@Serializable
data class AuthorIds(
    val mag: String?,
    val openalex: SerializedId?,
    val orcid: String?,
    val scopus: String?,
    val twitter: String?,
    val wikipedia: String?,
)
