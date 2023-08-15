package ktalex.dal

import ktalex.model.Publisher

class PublishersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/publishers"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Publisher? = getItem("$baseUrl/$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByRorId(id: String): Publisher? = getItem("$baseUrl/ror:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikidataId(id: String): Publisher? = getItem("$baseUrl/wikidata:$id")

}