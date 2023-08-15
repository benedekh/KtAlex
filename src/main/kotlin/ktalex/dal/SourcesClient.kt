package ktalex.dal

import ktalex.model.Institution
import ktalex.model.Source

class SourcesClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/sources"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Source? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Source? = getItem("$baseUrl/mag:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByIssn(id: String): Source? = getItem("$baseUrl/issn:$id")

    // TODO testme
    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByFatcatId(id: String): Source? = getItem("$baseUrl/fatcat:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikidataId(id: String): Source? = getItem("$baseUrl/wikidata:$id")

}