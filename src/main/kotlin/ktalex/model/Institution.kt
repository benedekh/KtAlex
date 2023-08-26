package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.WorkClient
import ktalex.dal.query.PageableQueryResponse
import ktalex.model.serialization.InstitutionTypeSerializer
import ktalex.model.serialization.RelationshipTypeSerializer
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedEnum
import ktalex.model.serialization.SerializedId

interface BaseInstitution {
    val countryCode: String?
    val displayName: String?
    val id: SerializedId?
    val ror: String?
    val type: SerializedEnum<InstitutionType>?
}

@Serializable
data class DehydratedInstitution(
    override val countryCode: String?,
    override val displayName: String?,
    override val id: SerializedId?,
    override val ror: String?,
    @Serializable(with = InstitutionTypeSerializer::class)
    override val type: SerializedEnum<InstitutionType>?,
) : BaseInstitution

@Serializable
data class AssociatedInstitution(
    override val countryCode: String?,
    override val displayName: String?,
    override val id: SerializedId?,
    override val ror: String?,
    @Serializable(with = InstitutionTypeSerializer::class)
    override val type: SerializedEnum<InstitutionType>?,
    @Serializable(with = RelationshipTypeSerializer::class)
    val relationship: SerializedEnum<RelationshipType>?,
) : BaseInstitution

@Serializable
data class Institution(
    val associatedInstitutions: List<AssociatedInstitution>?,
    val citedByCount: Int?,
    override val countryCode: String?,
    val countsByYear: List<CountsByYear>?,
    val createdDate: SerializedDate?,
    override val displayName: String?,
    val displayNameAcronyms: List<String>?,
    val displayNameAlternatives: List<String>?,
    val geo: Geo?,
    val homepageUrl: String?,
    override val id: SerializedId?,
    val ids: InstitutionIds?,
    val imageThumbnailUrl: String?,
    val imageUrl: String?,
    val international: CustomMaps?,
    val relevanceScore: Float?,
    val repositories: List<DehydratedSource>?,
    val roles: List<Role>?,
    override val ror: String?,
    val summaryStats: CitationMetrics?,
    @Serializable(with = InstitutionTypeSerializer::class)
    override val type: SerializedEnum<InstitutionType>?,
    val updatedDate: SerializedDateTime?,
    val worksApiUrl: String?,
    val worksCount: Int?,
    val xConcepts: List<RelatedConcept>?,
) : BaseInstitution {
    fun resolveWorks(): PageableQueryResponse<Work>? =
        worksApiUrl?.let { url -> WorkClient().use { it.getEntitiesByUrl(url) } }
}

enum class InstitutionType {
    EDUCATION, HEALTHCARE, COMPANY, ARCHIVE, NONPROFIT, GOVERNMENT, FACILITY, OTHER
}

enum class RelationshipType {
    PARENT, CHILD, RELATED
}

@Serializable
data class InstitutionIds(
    val grid: String?,
    val mag: Int?,
    val openalex: SerializedId?,
    val ror: SerializedId?,
    val wikipedia: String?,
    val wikidata: SerializedId?,
)
