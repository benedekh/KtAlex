package ktalex.dal

import ktalex.model.Author

class AuthorsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/authors"

    suspend fun getByOpenAlexId(id: String): Author? = getItem("$baseUrl/$id")

    suspend fun getByOrcid(id: String): Author? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Author? = getItem("$baseUrl/mag:$id")

}