package ktalex.dal

import ktalex.model.Source

class SourcesClient : BaseClient<Source>() {

    override val baseUrl = "$openAlexBaseUrl/sources"

    override fun getRandom(): Source = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Source? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Source? = getItem("$baseUrl/mag:$id")

    fun getByIssn(id: String): Source? = getItem("$baseUrl/issn:$id")

    fun getByWikidataId(id: String): Source? = getItem("$baseUrl/wikidata:$id")

}