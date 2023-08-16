package ktalex.dal

import ktalex.model.Funder

class FundersClient : BaseClient<Funder>() {

    override val baseUrl = "${openAlexBaseUrl}/funders"

    override fun getRandom(): Funder = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Funder? = getItem("$baseUrl/$id")

    fun getByRorId(id: String): Funder? = getItem("$baseUrl/ror:$id")

    fun getByWikidataId(id: String): Funder? = getItem("$baseUrl/wikidata:$id")

}