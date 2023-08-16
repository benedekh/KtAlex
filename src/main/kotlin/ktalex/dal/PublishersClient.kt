package ktalex.dal

import ktalex.model.Publisher

class PublishersClient : BaseClient<Publisher>() {

    override val baseUrl = "$openAlexBaseUrl/publishers"

    override fun getRandom(): Publisher = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Publisher? = getItem("$baseUrl/$id")

    fun getByRorId(id: String): Publisher? = getItem("$baseUrl/ror:$id")

    fun getByWikidataId(id: String): Publisher? = getItem("$baseUrl/wikidata:$id")

}