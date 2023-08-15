package ktalex.dal

import ktalex.model.Publisher

class PublishersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/publishers"

    suspend fun getByOpenAlexId(id: String): Publisher? = getItem("$baseUrl/$id")

    suspend fun getByRorId(id: String): Publisher? = getItem("$baseUrl/ror:$id")

    suspend fun getByWikidataId(id: String): Publisher? = getItem("$baseUrl/wikidata:$id")

}