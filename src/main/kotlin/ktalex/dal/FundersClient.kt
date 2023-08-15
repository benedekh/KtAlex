package ktalex.dal

import ktalex.model.Funder

class FundersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/funders"

    suspend fun getByOpenAlexId(id: String): Funder? = getItem("$baseUrl/$id")

    suspend fun getByRorId(id: String): Funder? = getItem("$baseUrl/ror:$id")

    suspend fun getByWikidataId(id: String): Funder? = getItem("$baseUrl/wikidata:$id")

}