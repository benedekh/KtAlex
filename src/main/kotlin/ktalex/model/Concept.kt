package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
abstract class BaseConcept {
    abstract val displayName: String
    abstract val id: String
    abstract val level: Int
    abstract val wikidata: String
}

@Serializable
data class DehydratedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String
) : BaseConcept()

@Serializable
data class RelatedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String,
    val score: Float
) : BaseConcept()

@Serializable
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
) : BaseConcept() {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

@Serializable
data class ConceptIds(
    val mag: String,
    val openalex: String,
    val umlsCui: String,
    val umlsAui: String,
    val wikidata: String,
    val wikipedia: String
)