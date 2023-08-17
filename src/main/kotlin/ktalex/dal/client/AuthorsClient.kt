package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Author
import ktalex.dal.query.QueryResponse

class AuthorsClient : BaseClient<Author>() {

    override val entityType = "authors"

    override fun getRandom(queryBuilder: QueryBuilder?): Author = getEntity(
        "$baseUrl/random${queryBuilder?.build() ?: ""}"
    )!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResponse<Author> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByOrcid(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

}