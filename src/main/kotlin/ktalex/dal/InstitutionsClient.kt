package ktalex.dal

import ktalex.model.Institution

class InstitutionsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/institutions"

    suspend fun getByOpenAlexId(id: String): Institution? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Institution? = getItem("$baseUrl/mag:$id")

    suspend fun getByRorId(id: String): Institution? = getItem("$baseUrl/ror:$id")

    suspend fun getByWikidataId(id: String): Institution? = getItem("$baseUrl/wikidata:$id")

}