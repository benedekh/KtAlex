package ktalex.dal

import ktalex.model.Author

class AuthorsClient : BaseClient() {

    override val baseUrl = "${OPENALEX_BASE_URL}/authors"

    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByOpenAlexId(id: String): Author? = getItem("$baseUrl/$id")

    suspend fun getByOrcid(id: String): Author? = getItem("$baseUrl/$id")

    suspend fun getByMicrosoftAcademicGraphId(id: String): Author? = getItem("$baseUrl/mag:$id")

    // TODO testme
    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByScopusId(id: String): Author? = getItem("$baseUrl/twitter:$id")

    // TODO testme
    // TODO add method to extract ID from URL in the *Ids class
    suspend fun getByWikipediaId(id: String): Author? = getItem("$baseUrl/wikipedia:$id")

}