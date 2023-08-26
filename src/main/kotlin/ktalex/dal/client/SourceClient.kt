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

    /**
     * Get a random [Source]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Source =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Source] by its URL.
     *
     * @param url the [Source]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Source> = getEntity(url)

    /**
     * Get a list of [Source]s by their URL.
     *
     * @param url the URL of a list of [Source]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Source> = getEntitiesInternal(url)

    /**
     * Get a list of [Source]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Source]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param url the URL of a list of [Source]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get a [Source] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param id the OpenAlex ID of the [Source]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Source =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Source] by its Microsoft Academic Graph ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param id the Microsoft Academic Graph ID of the [Source]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Source =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Source] by its ISSN. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param id the ISSN of the [Source]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByIssn(id: String, queryBuilder: QueryBuilder? = null): Source =
        getEntity("$baseUrl/issn:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Source] by its Wikidata ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/sources/filter-sources)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/sources/search-sources)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/sources/group-sources)
     *
     * @param id the Wikidata ID of the [Source]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Source =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
