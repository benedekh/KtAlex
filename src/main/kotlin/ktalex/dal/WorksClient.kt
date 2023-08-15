package ktalex.dal

import ktalex.model.Work

class WorksClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/works"

    suspend fun getByOpenAlexId(id: String): Work? = getItem("$baseUrl/$id")

    suspend fun getByDoi(id: String): Work? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Work? = getItem("$baseUrl/mag:$id")

    suspend fun getByPubMedId(id: String): Work? = getItem("$baseUrl/pmid:$id")

}