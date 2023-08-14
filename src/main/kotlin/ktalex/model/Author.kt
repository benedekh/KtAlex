package ktalex.model

import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

abstract class BaseAuthor(
    open val id: String,
    open val displayName: String,
    open val orcid: String
)

data class DehydratedAuthor(
    override val id: String,
    override val displayName: String,
    override val orcid: String
) : BaseAuthor(id, displayName, orcid)

data class Author(
    val citedByCount: Int,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
    override val displayName: String,
    val displayNameAlternatives: List<String>,
    override val id: String,
    val ids: AuthorIds,
    val lastKnownInstitution: DehydratedInstitution,
    override val orcid: String,
    val summaryStats: CitationMetrics,
    val updatedDate: String,
    val worksApiUrl: String,
    val worksCount: List<CountsByYear>,
    val xConcepts: List<RelatedConcept>
) : BaseAuthor(id, displayName, orcid) {
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

data class AuthorIds(
    val mag: String,
    val openalex: String,
    val orcid: String,
    val scopus: String,
    val twitter: String,
    val wikipedia: String
)