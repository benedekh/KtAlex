package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Author

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class AuthorClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Author>(openAlexBaseUrl, mailTo) {

    override val entityType = "authors"

    override fun getRandom(queryBuilder: QueryBuilder?): Author =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Author> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Author> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByOrcid(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/https://orcid.org/$id${queryBuilder?.build().orEmpty()}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Author? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")
}
