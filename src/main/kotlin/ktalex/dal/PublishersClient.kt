package ktalex.dal

import ktalex.model.Publisher

class PublishersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/publishers"

    fun getByOpenAlexId(id: String): Publisher? = getItem("$baseUrl/$id")

    fun getByRorId(id: String): Publisher? = getItem("$baseUrl/ror:$id")

    fun getByWikidataId(id: String): Publisher? = getItem("$baseUrl/wikidata:$id")

}