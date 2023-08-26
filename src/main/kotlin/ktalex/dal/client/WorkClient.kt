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

    /**
     * Get a random [Work]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Work =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Work] by its URL.
     *
     * @param url the [Work]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Work> = getEntity(url)

    /**
     * Get a list of [Work]s by their URL.
     *
     * @param url the URL of a list of [Work]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Work> = getEntitiesInternal(url)

    /**
     * Get a list of [Work]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Work]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param url the URL of a list of [Work]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get a [Work] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param id the OpenAlex ID of the [Work]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Work =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Work] by DOI. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param id the DOI of the [Work]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByDoi(id: String, queryBuilder: QueryBuilder? = null): Work =
        getEntity("$baseUrl/https://doi.org/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Work] by its Microsoft Academic Graph ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param id the Microsoft Academic Graph ID of the [Work]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Work =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Work] by its PubMed ID (PMID). [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/works/filter-works)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/works/search-works)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/works/group-works)
     *
     * @param id the PubMed ID (PMID) of the [Work]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByPubMedId(id: String, queryBuilder: QueryBuilder? = null): Work =
        getEntity("$baseUrl/pmid:$id${queryBuilder?.build().orEmpty()}")
}
