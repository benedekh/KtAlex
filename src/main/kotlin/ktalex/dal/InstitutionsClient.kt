package ktalex.dal

import ktalex.model.Institution

class InstitutionsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/institutions"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Institution? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Institution? = getItem("$baseUrl/mag:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByRorId(id: String): Institution? = getItem("$baseUrl/ror:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikidataId(id: String): Institution? = getItem("$baseUrl/wikidata:$id")

}