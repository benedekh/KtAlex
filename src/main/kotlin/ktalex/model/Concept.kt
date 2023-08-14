package ktalex.model

import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

abstract class BaseConcept(
    open val displayName: String,
    open val id: String,
    open val level: Int,
    open val wikidata: String
)

data class DehydratedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String
) : BaseConcept(displayName, id, level, wikidata)

data class RelatedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String,
    val score: Float
) : BaseConcept(displayName, id, level, wikidata)

data class Concept(
    val ancestors: List<DehydratedConcept>,
    val citedByCount: Int,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
    val description: String,
    override val displayName: String,
    override val id: String,
    val ids: ConceptIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val international: DisplayNames,
    override val level: Int,
    val relatedConcepts: List<RelatedConcept>,
    val summaryStats: CitationMetrics,
    val updatedDate: String,
    override val wikidata: String,
    val worksApiUrl: String, // TODO An URL that will get you a list of all the works tagged with this concept.
    val worksCount: Int
) : BaseConcept(displayName, id, level, wikidata) {
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

data class ConceptIds(
    val mag: String,
    val openalex: String,
    val umlsCui: String,
    val umlsAui: String,
    val wikidata: String,
    val wikipedia: String
)