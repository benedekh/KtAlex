package ktalex.dal

import ktalex.model.Work

class WorksClient : BaseClient<Work>() {

    override val baseUrl = "$openAlexBaseUrl/works"

    override fun getRandom(): Work = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Work? = getItem("$baseUrl/$id")

    fun getByDoi(id: String): Work? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Work? = getItem("$baseUrl/mag:$id")

    fun getByPubMedId(id: String): Work? = getItem("$baseUrl/pmid:$id")

}