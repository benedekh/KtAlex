package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorksClient
import ktalex.dal.query.QueryResponse
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

@Serializable
abstract class BaseConcept {
    abstract val displayName: String?
    abstract val id: SerializedId?
    abstract val level: Int?
    abstract val wikidata: String?
}

@Serializable
data class DehydratedConcept(
    override val displayName: String?,
    override val id: SerializedId?,
    override val level: Int?,
    override val wikidata: String?
) : BaseConcept()

@Serializable
data class RelatedConcept(
    override val displayName: String?,
    override val id: SerializedId?,
    override val level: Int?,
    override val wikidata: String?,
    val score: Float?
) : BaseConcept()

@Serializable
data class Concept(
    val ancestors: List<DehydratedConcept>?,
    val citedByCount: Int?,
    val countsByYear: List<CountsByYear>?,
    val createdDate: SerializedDate?,
    val description: String?,
    override val displayName: String?,
    override val id: SerializedId?,
    val ids: ConceptIds?,
    val imageThumbnailUrl: String?,
    val imageUrl: String?,
    val international: CustomMaps?,
    override val level: Int,
    val relatedConcepts: List<RelatedConcept>?,
    val relevanceScore: Float?,
    val summaryStats: CitationMetrics?,
    val updatedDate: SerializedDateTime?,
    override val wikidata: String?,
    val worksApiUrl: String?,
    val worksCount: Int?
) : BaseConcept() {
    // TODO testme
    fun resolveWorks(): QueryResponse<Work>? = worksApiUrl?.let { WorksClient().getEntities(it) }
}

@Serializable
data class ConceptIds(
    val mag: String?,
    val openalex: SerializedId?,
    val umlsCui: List<String>?,
    val umlsAui: String?,
    val wikidata: SerializedId?,
    val wikipedia: String?
)