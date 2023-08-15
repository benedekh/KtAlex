package ktalex.dal

import ktalex.model.Source

class SourcesClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/sources"

    suspend fun getByOpenAlexId(id: String): Source? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Source? = getItem("$baseUrl/mag:$id")

    suspend fun getByIssn(id: String): Source? = getItem("$baseUrl/issn:$id")

    suspend fun getByWikidataId(id: String): Source? = getItem("$baseUrl/wikidata:$id")

}