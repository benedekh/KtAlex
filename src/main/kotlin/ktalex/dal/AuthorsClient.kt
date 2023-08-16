package ktalex.dal

import ktalex.model.Author

class AuthorsClient : BaseClient<Author>() {

    override val baseUrl = "${openAlexBaseUrl}/authors"

    override fun getRandom(): Author = getItem("$baseUrl/random")!!

    fun getByOpenAlexId(id: String): Author? = getItem("$baseUrl/$id")

    fun getByOrcid(id: String): Author? = getItem("$baseUrl/$id")

    fun getByMicrosoftAcademicGraphId(id: String): Author? = getItem("$baseUrl/mag:$id")

}