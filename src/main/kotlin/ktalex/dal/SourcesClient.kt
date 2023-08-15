package ktalex.dal

import ktalex.model.Source

class SourcesClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/sources"

    fun getByOpenAlexId(id: String): Source? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Source? = getItem("$baseUrl/mag:$id")

    fun getByIssn(id: String): Source? = getItem("$baseUrl/issn:$id")

    fun getByWikidataId(id: String): Source? = getItem("$baseUrl/wikidata:$id")

}