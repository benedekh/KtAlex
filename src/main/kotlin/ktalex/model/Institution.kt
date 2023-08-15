package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedEnum

@Serializable
abstract class BaseInstitution {
    abstract val countryCode: String?
    abstract val displayName: String
    abstract val id: String
    abstract val ror: String?
    abstract val type: SerializedEnum<InstitutionType>?
}

@Serializable
data class DehydratedInstitution(
    override val countryCode: String?,
    override val displayName: String,
    override val id: String,
    override val ror: String?,
    override val type: SerializedEnum<InstitutionType>?
) : BaseInstitution()

@Serializable
data class AssociatedInstitution(
    override val countryCode: String?,
    override val displayName: String,
    override val id: String,
    override val ror: String?,
    override val type: SerializedEnum<InstitutionType>?,
    val relationship: SerializedEnum<RelationshipType>
) : BaseInstitution()

@Serializable
data class Institution(
    val associatedInstitutions: List<AssociatedInstitution>,
    val citedByCount: Int,
    override val countryCode: String?,
    val countsByYear: List<CountsByYear>,
    val createdDate: SerializedDate,
    override val displayName: String,
    val displayNameAcronyms: List<String>,
    val displayNameAlternatives: List<String>,
    val geo: Geo,
    val homepageUrl: String,
    override val id: String,
    val ids: InstitutionIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val international: CustomMaps,
    val repositories: List<DehydratedSource>,
    val roles: List<Role>,
    override val ror: String?,
    val summaryStats: CitationMetrics,
    override val type: SerializedEnum<InstitutionType>?,
    val updatedDate: SerializedDateTime,
    val worksApiUrl: String, // TODO A URL that will get you a list of all the Works affiliated with this institution.
    val worksCount: Int,
    val xConcepts: List<RelatedConcept>
) : BaseInstitution()

enum class InstitutionType {
    EDUCATION, HEALTHCARE, COMPANY, ARCHIVE, NONPROFIT, GOVERNMENT, FACILITY, OTHER
}

enum class RelationshipType {
    PARENT, CHILD, RELATED
}

@Serializable
data class InstitutionIds(
    val grid: String,
    val mag: Int,
    val openalex: String,
    val ror: String,
    val wikipedia: String,
    val wikidata: String
)