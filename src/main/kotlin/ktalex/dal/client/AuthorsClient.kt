package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Author

class AuthorsClient : BaseEntityClient<Author>() {

    override val entityType = "authors"

    override fun getRandom(queryBuilder: QueryBuilder?): Author =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(url: String): QueryResponse<Author> = getEntity(url)!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByOrcid(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

}