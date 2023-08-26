package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Funder

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class FunderClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Funder>(openAlexBaseUrl, mailTo) {

    override val entityType = "funders"

    override fun getRandom(queryBuilder: QueryBuilder?): Funder =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Funder> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Funder> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Funder> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Funder> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
