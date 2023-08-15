package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime

@Serializable
abstract class BaseConcept {
    abstract val displayName: String
    abstract val id: String
    abstract val level: Int
    abstract val wikidata: String?
}

@Serializable
data class DehydratedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String?
) : BaseConcept()

@Serializable
data class RelatedConcept(
    override val displayName: String,
    override val id: String,
    override val level: Int,
    override val wikidata: String?,
    val score: Float
) : BaseConcept()

@Serializable
data class Concept(
    val ancestors: List<DehydratedConcept>,
    val citedByCount: Int,
    val countsByYear: List<CountsByYear>,
    val createdDate: SerializedDate,
    val description: String,
    override val displayName: String,
    override val id: String,
    val ids: ConceptIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val international: CustomMaps,
    override val level: Int,
    val relatedConcepts: List<RelatedConcept>,
    val summaryStats: CitationMetrics,
    val updatedDate: SerializedDateTime,
    override val wikidata: String?,
    val worksApiUrl: String, // TODO An URL that will get you a list of all the works tagged with this concept.
    val worksCount: Int
) : BaseConcept()

@Serializable
data class ConceptIds(
    val mag: String,
    val openalex: String,
    val umlsCui: List<String>?,
    val umlsAui: String?,
    val wikidata: String,
    val wikipedia: String
)