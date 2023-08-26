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

    /**
     * Get a random [Publisher]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Publisher =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Publisher] by its URL.
     *
     * @param url the [Publisher]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Publisher> = getEntity(url)

    /**
     * Get a list of [Publisher]s by their URL.
     *
     * @param url the URL of a list of [Publisher]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Publisher> = getEntitiesInternal(url)

    /**
     * Get a list of [Publisher]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Publisher> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Publisher]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param url the URL of a list of [Publisher]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Publisher> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get a [Publisher] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param id the OpenAlex ID of the [Publisher]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Publisher] by its ROR ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param id the ROR ID of the [Publisher]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Publisher] by its Wikidata ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/publishers/filter-publishers)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/publishers/search-publishers)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/publishers/group-publishers)
     *
     * @param id the Wikidata ID of the [Publisher]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Publisher =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
