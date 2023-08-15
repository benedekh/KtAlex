package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime

@Serializable
abstract class BaseAuthor {
    abstract val id: String
    abstract val displayName: String
    abstract val orcid: String?
}

@Serializable
data class DehydratedAuthor(
    override val id: String,
    override val displayName: String,
    override val orcid: String?
) : BaseAuthor()

@Serializable
data class Author(
    val citedByCount: Int,
    val countsByYear: List<CountsByYear>,
    val createdDate: SerializedDate,
    override val displayName: String,
    val displayNameAlternatives: List<String>,
    override val id: String,
    val ids: AuthorIds,
    val lastKnownInstitution: DehydratedInstitution,
    override val orcid: String?,
    val summaryStats: CitationMetrics,
    val updatedDate: SerializedDateTime,
    val worksApiUrl: String,
    val worksCount: Int,
    val xConcepts: List<RelatedConcept>
) : BaseAuthor()

@Serializable
data class AuthorIds(
    val mag: String?,
    val openalex: String,
    val orcid: String,
    val scopus: String,
    val twitter: String?,
    val wikipedia: String?
)