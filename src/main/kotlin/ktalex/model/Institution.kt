package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import ktalex.utils.EnumUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
abstract class BaseInstitution {
    abstract val countryCode: String
    abstract val displayName: String
    abstract val id: String
    abstract val ror: String
    abstract val type: String

    val typeEnum: InstitutionType?
        get() = EnumUtil.valueOfOrNull<InstitutionType>(type)
}

@Serializable
data class DehydratedInstitution(
    override val countryCode: String,
    override val displayName: String,
    override val id: String,
    override val ror: String,
    override val type: String
) : BaseInstitution()

@Serializable
data class AssociatedInstitution(
    override val countryCode: String,
    override val displayName: String,
    override val id: String,
    override val ror: String,
    override val type: String,
    val relationship: String
) : BaseInstitution() {
    val relationshipEnum: RelationshipType? = EnumUtil.valueOfOrNull<RelationshipType>(relationship)
}

@Serializable
data class Institution(
    val associatedInstitutions: List<AssociatedInstitution>,
    val citedByCount: Int,
    override val countryCode: String,
    val countsByYear: List<CountsByYear>,
    val createdDate: String,
    override val displayName: String,
    val displayNameAcronyms: List<String>,
    val displayNameAlternatives: List<String>,
    val geo: Geo,
    val homepageUrl: String,
    override val id: String,
    val ids: InstitutionIds,
    val imageThumbnailUrl: String,
    val imageUrl: String,
    val international: DisplayNames,
    val repositories: List<DehydratedSource>,
    val roles: List<Role>,
    override val ror: String,
    val summaryStats: CitationMetrics,
    override val type: String,
    val updatedDate: String,
    val worksApiUrl: String, // TODO A URL that will get you a list of all the Works affiliated with this institution.
    val worksCount: Int,
    val xConcepts: List<RelatedConcept>
) : BaseInstitution() {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

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