package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Publisher

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class PublisherClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Publisher>(openAlexBaseUrl, mailTo) {

    override val entityType = "publishers"

    override fun getRandom(queryBuilder: QueryBuilder?): Publisher =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Publisher> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Publisher> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Publisher> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Publisher> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
