package ktalex.model

import ktalex.utils.DateUtil
import ktalex.utils.EnumUtil
import java.time.LocalDate
import java.time.LocalDateTime

abstract class BaseInstitution(
    open val countryCode: String,
    open val displayName: String,
    open val id: String,
    open val ror: String,
    open val type: String
) {
    val typeEnum: InstitutionType?
        get() = EnumUtil.valueOfOrNull<InstitutionType>(type)
}

data class DehydratedInstitution(
    override val countryCode: String,
    override val displayName: String,
    override val id: String,
    override val ror: String,
    override val type: String
) : BaseInstitution(countryCode, displayName, id, ror, type)

data class AssociatedInstitution(
    override val countryCode: String,
    override val displayName: String,
    override val id: String,
    override val ror: String,
    override val type: String,
    val relationship: String
) : BaseInstitution(countryCode, displayName, id, ror, type) {
    val relationshipEnum: RelationshipType? = EnumUtil.valueOfOrNull<RelationshipType>(relationship)
}

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
) : BaseInstitution(countryCode, displayName, id, ror, type) {
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

enum class InstitutionType {
    EDUCATION, HEALTHCARE, COMPANY, ARCHIVE, NONPROFIT, GOVERNMENT, FACILITY, OTHER
}

enum class RelationshipType {
    PARENT, CHILD, RELATED
}

data class InstitutionIds(
    val grid: String,
    val mag: Int,
    val openalex: String,
    val ror: String,
    val wikipedia: String,
    val wikidata: String
)