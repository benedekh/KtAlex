package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Author

class AuthorsClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Author>(openAlexBaseUrl, mailTo) {

    override val entityType = "authors"

    override fun getRandom(queryBuilder: QueryBuilder?): Author =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntityWithExactType(url: String): QueryResponse<Author> = getEntity(url)!!

    override fun getEntities(url: String): PageableQueryResponse<Author> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByOrcid(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")
}
