package ktalex.dal

import ktalex.model.Work

class WorksClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/works"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Work? = getItem("$baseUrl/$id")

    suspend fun getByDoi(id: String): Work? = getItem("$baseUrl/$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByMicrosoftAcademicGraphId(id: String): Work? = getItem("$baseUrl/mag:$id")

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByPubMedId(id: String): Work? = getItem("$baseUrl/pmid:$id")

}