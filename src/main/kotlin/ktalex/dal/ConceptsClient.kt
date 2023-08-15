package ktalex.dal

import ktalex.model.Concept

class ConceptsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/concepts"

    fun getByOpenAlexId(id: String): Concept? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Concept? = getItem("$baseUrl/mag:$id")

    fun getByWikidataId(id: String): Concept? = getItem("$baseUrl/wikidata:$id")

}