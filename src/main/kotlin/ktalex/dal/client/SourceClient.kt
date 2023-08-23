package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Source

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class SourceClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Source>(openAlexBaseUrl, mailTo) {

    override val entityType = "sources"

    override fun getRandom(queryBuilder: QueryBuilder?): Source =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Source> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Source> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    fun getByIssn(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/issn:$id${queryBuilder?.build().orEmpty()}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
