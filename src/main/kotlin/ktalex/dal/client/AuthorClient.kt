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

    /**
     * Get a random [Author]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Author =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Author] by its URL.
     *
     * @param url the [Author]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Author> = getEntity(url)

    /**
     * Get a list of [Author]s by their URL.
     *
     * @param url the URL of a list of [Author]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Author> = getEntitiesInternal(url)

    /**
     * Get a list of [Author]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Author]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param url the URL of a list of [Author]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Author> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get an [Author] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param id the OpenAlex ID of the [Author]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Author =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Author] by its ORCID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param id the ORCID of the [Author]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOrcid(id: String, queryBuilder: QueryBuilder? = null): Author =
        getEntity("$baseUrl/https://orcid.org/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get an [Author] by its Microsoft Academic Graph ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/authors/filter-authors)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/authors/search-authors)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/authors/group-authors)
     *
     * @param id the Microsoft Academic Graph ID of the [Author]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Author =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")
}
