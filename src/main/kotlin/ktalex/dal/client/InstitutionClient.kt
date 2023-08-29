package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Institution

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class InstitutionClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Institution>(openAlexBaseUrl, mailTo) {

    override val entityType = "institutions"

    /**
     * Get a random [Institution]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Institution =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Institution] by its URL.
     *
     * @param url the [Institution]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Institution> = getEntity(url)

    /**
     * Get a list of [Institution]s by their URL.
     *
     * @param url the URL of a list of [Institution]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Institution> = getEntitiesInternal(url)

    /**
     * Get a list of [Institution]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Institution> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Institution]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param url the URL of a list of [Institution]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Institution> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get an [Institution] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param id the OpenAlex ID of the [Institution]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Institution =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Institution] by its Microsoft Academic Graph ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param id the Microsoft Academic Graph ID of the [Institution]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Institution =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Institution] by its ROR ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param id the ROR ID of the [Institution]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Institution =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Institution] by its Wikidata ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/institutions/filter-institutions)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/institutions/search-institutions)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/institutions/group-institutions)
     *
     * @param id the Wikidata ID of the [Institution]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Institution =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
