package ktalex.dal

import ktalex.model.Concept

class ConceptsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/concepts"

    suspend fun getByOpenAlexId(id: String): Concept? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Concept? = getItem("$baseUrl/mag:$id")

    suspend fun getByWikidataId(id: String): Concept? = getItem("$baseUrl/wikidata:$id")

}