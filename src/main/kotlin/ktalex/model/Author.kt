package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

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
    val createdDate: String,
    override val displayName: String,
    val displayNameAlternatives: List<String>,
    override val id: String,
    val ids: AuthorIds,
    val lastKnownInstitution: DehydratedInstitution,
    override val orcid: String?,
    val summaryStats: CitationMetrics,
    val updatedDate: String,
    val worksApiUrl: String,
    val worksCount: Int,
    val xConcepts: List<RelatedConcept>
) : BaseAuthor() {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

@Serializable
data class AuthorIds(
    val mag: String?,
    val openalex: String,
    val orcid: String,
    val scopus: String,
    val twitter: String?,
    val wikipedia: String?
)