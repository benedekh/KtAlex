package ktalex.dal

import ktalex.model.Institution

class InstitutionsClient : BaseClient<Institution>() {

    override val baseUrl = "$openAlexBaseUrl/institutions"

    override fun getRandom(): Institution = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Institution? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Institution? = getItem("$baseUrl/mag:$id")

    fun getByRorId(id: String): Institution? = getItem("$baseUrl/ror:$id")

    fun getByWikidataId(id: String): Institution? = getItem("$baseUrl/wikidata:$id")

}