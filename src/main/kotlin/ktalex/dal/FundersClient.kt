package ktalex.dal

import ktalex.model.Funder

class FundersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/funders"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Funder? = getItem("$baseUrl/$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByRorId(id: String): Funder? = getItem("$baseUrl/ror:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikidataId(id: String): Funder? = getItem("$baseUrl/wikidata:$id")

}