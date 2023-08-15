package ktalex.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ktalex.utils.DateUtil
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class Work(
    val abstractInvertedIndex: Map<String, List<Int>>,
    val authorships: List<Authorship>,
    val apcList: Apc,
    val apcPaid: Apc,
    val bestOaLocation: Location?,
    val biblio: Biblio,
    val citedByApiUrl: String,
    val citedByCount: Int,
    val concepts: List<RelatedConcept>,
    val correspondingAuthorIds: List<String>, // TODO query by ID
    val correspondingInstitutionIds: List<String>, // TODO query by ID
    val countsByYear: List<CitedByCountYear>,
    val createdDate: String,
    val displayName: String,
    val doi: String,
    val grants: List<Grant>,
    val id: String,
    val ids: WorkIds,
    val institutionsDistinctCount: Int,
    val isOa: Boolean?,
    val isParatext: Boolean,
    val isRetracted: Boolean,
    val language: String,
    val license: String?,
    val locations: List<Location>,
    val locationsCount: Int,
    val mesh: List<Mesh>,
    val ngramsUrl: String, // TODO query by ngrams
    val openAccess: OpenAccess,
    val primaryLocation: Location,
    val publicationDate: String,
    val publicationYear: Int,
    val referencedWorks: List<String>, // TODO query by ID
    val referencedWorksCount: Int,
    val relatedWorks: List<String>, // TODO query by ID
    val sustainableDevelopmentGoals: List<SustainableDevelopmentGoal>,
    val title: String,
    val type: String,
    val typeCrossref: String,
    val updatedDate: String,
) {
    @Contextual // TODO replace with class that holds both string and type-specific field
    val createdDateAsDate: LocalDate? = DateUtil.toDate(createdDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val publicationDateAsDate: LocalDate? = DateUtil.toDate(publicationDate)

    @Contextual // TODO replace with class that holds both string and type-specific field
    val updatedDateAsDateTime: LocalDateTime? = DateUtil.toDateTime(updatedDate)
}

@Serializable
data class WorkIds(
    val doi: String,
    val mag: String,
    val openalex: String,
    val pmid: String,
    val pmcid: String?
)