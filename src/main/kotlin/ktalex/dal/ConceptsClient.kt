package ktalex.dal

import ktalex.model.Concept

class ConceptsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/concepts"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Concept? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Concept? = getItem("$baseUrl/mag:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikidataId(id: String): Concept? = getItem("$baseUrl/wikidata:$id")

}