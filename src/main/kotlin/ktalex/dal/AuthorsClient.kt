package ktalex.dal

import ktalex.model.Author

class AuthorsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/authors"

    fun getByOpenAlexId(id: String): Author? = getItem("$baseUrl/$id")

    fun getByOrcid(id: String): Author? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Author? = getItem("$baseUrl/mag:$id")

}