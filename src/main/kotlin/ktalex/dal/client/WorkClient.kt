package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Work

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class WorkClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Work>(openAlexBaseUrl, mailTo) {

    override val entityType = "works"

    override fun getRandom(queryBuilder: QueryBuilder?): Work =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Work> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Work> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByDoi(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/https://doi.org/$id${queryBuilder?.build().orEmpty()}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    fun getByPubMedId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/pmid:$id${queryBuilder?.build().orEmpty()}")
}
