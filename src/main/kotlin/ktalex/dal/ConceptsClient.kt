package ktalex.dal

import ktalex.model.Concept

class ConceptsClient : BaseClient<Concept>() {

    override val baseUrl = "${openAlexBaseUrl}/concepts"

    override fun getRandom(): Concept = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Concept? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Concept? = getItem("$baseUrl/mag:$id")

    fun getByWikidataId(id: String): Concept? = getItem("$baseUrl/wikidata:$id")

}