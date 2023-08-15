package ktalex.dal

import ktalex.model.Work

class WorksClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/works"

    fun getByOpenAlexId(id: String): Work? = getItem("$baseUrl/$id")

    fun getByDoi(id: String): Work? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Work? = getItem("$baseUrl/mag:$id")

    fun getByPubMedId(id: String): Work? = getItem("$baseUrl/pmid:$id")

}