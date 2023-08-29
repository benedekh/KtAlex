package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.dal.client.NgramClient
import ktalex.dal.client.WorkClient
import ktalex.dal.ngrams.NgramsResponse
import ktalex.dal.query.PageableQueryResponse
import ktalex.model.serialization.ResolvableEntity
import ktalex.model.serialization.SerializedDate
import ktalex.model.serialization.SerializedDateTime
import ktalex.model.serialization.SerializedId

@Serializable
data class Work(
    val abstractInvertedIndex: Map<String, List<Int>>?,
    val authorships: List<Authorship>?,
    val apcList: Apc?,
    val apcPaid: Apc?,
    val bestOaLocation: Location?,
    val biblio: Biblio?,
    val citedByApiUrl: String?,
    val citedByCount: Int?,
    val concepts: List<RelatedConcept>?,
    val correspondingAuthorIds: List<ResolvableEntity<Author>>?,
    val correspondingInstitutionIds: List<ResolvableEntity<Institution>>?,
    val countsByYear: List<CitedByCountYear>?,
    val createdDate: SerializedDate?,
    val displayName: String?,
    val doi: String?,
    val grants: List<Grant>?,
    val id: SerializedId?,
    val ids: WorkIds?,
    val institutionsDistinctCount: Int?,
    val isAuthorsTruncated: Boolean?,
    val isOa: Boolean?,
    val isParatext: Boolean?,
    val isRetracted: Boolean?,
    val language: String?,
    val license: String?,
    val locations: List<Location>?,
    val locationsCount: Int?,
    val mesh: List<Mesh>?,
    val ngramsUrl: String?,
    val openAccess: OpenAccess?,
    val primaryLocation: Location?,
    val publicationDate: SerializedDate?,
    val publicationYear: Int?,
    val referencedWorks: List<ResolvableEntity<Work>>?,
    val referencedWorksCount: Int?,
    val relevanceScore: Float?,
    val relatedWorks: List<ResolvableEntity<Work>>?,
    val sustainableDevelopmentGoals: List<SustainableDevelopmentGoal>?,
    val title: String?,
    val type: String?,
    val typeCrossref: String?,
    val updatedDate: SerializedDateTime?,
) {
    fun resolveCitedBys(): PageableQueryResponse<Work>? =
        citedByApiUrl?.let { url -> WorkClient().getEntitiesByUrl(url) }

    fun resolveNgrams(): NgramsResponse? = ngramsUrl?.let { url -> NgramClient().getNgrams(url) }
}

@Serializable
data class WorkIds(
    val doi: String?,
    val mag: String?,
    val openalex: SerializedId?,
    val pmid: SerializedId?,
    val pmcid: String?,
)
