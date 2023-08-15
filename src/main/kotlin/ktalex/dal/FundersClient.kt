package ktalex.dal

import ktalex.model.Funder

class FundersClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/funders"

    fun getByOpenAlexId(id: String): Funder? = getItem("$baseUrl/$id")

    fun getByRorId(id: String): Funder? = getItem("$baseUrl/ror:$id")

    fun getByWikidataId(id: String): Funder? = getItem("$baseUrl/wikidata:$id")

}