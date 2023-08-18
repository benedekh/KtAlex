package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorksClient
import ktalex.dal.query.QueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

@Serializable
abstract class BaseAuthor {
    abstract val id: SerializedId?
    abstract val displayName: String?
    abstract val orcid: String?
}

@Serializable
data class DehydratedAuthor(
    override val id: SerializedId?,
    override val displayName: String?,
    override val orcid: String?
) : BaseAuthor()

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
    val xConcepts: List<RelatedConcept>?
) : BaseAuthor() {
    fun resolveWorks(): QueryResponse<Work>? = worksApiUrl?.let { WorksClient().getEntities(it) }
}

@Serializable
data class AuthorIds(
    val mag: String?,
    val openalex: SerializedId?,
    val orcid: String?,
    val scopus: String?,
    val twitter: String?,
    val wikipedia: String?
)