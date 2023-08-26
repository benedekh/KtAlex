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

    /**
     * Get a random [Funder]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Funder =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Funder] by its URL.
     *
     * @param url the [Funder]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Funder> = getEntity(url)

    /**
     * Get a list of [Funder]s by their URL.
     *
     * @param url the URL of a list of [Funder]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Funder> = getEntitiesInternal(url)

    /**
     * Get a list of [Funder]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Funder> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Funder]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param url the URL of a list of [Funder]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Funder> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get a [Funder] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param id the OpenAlex ID of the [Funder]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Funder] by its ROR ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param id the ROR ID of the [Funder]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Funder] by its Wikidata ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/funders/filter-funders)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/funders/search-funders)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/funders/group-funders)
     *
     * @param id the Wikidata ID of the [Funder]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Funder =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
