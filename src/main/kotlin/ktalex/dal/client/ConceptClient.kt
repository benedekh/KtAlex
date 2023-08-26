package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Concept

/**
 * @param openAlexBaseUrl the base URL of the OpenAlex API. Defaults to https://api.openalex.org
 * @param mailTo excerpt from the OpenAlex documentation: "The polite pool has much faster and more consistent
 * response times. To get into the polite pool, you just have to set mailTo to an email address where they can
 * contact you."
 * [Source](https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool)
 */
class ConceptClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Concept>(openAlexBaseUrl, mailTo) {

    override val entityType = "concepts"

    /**
     * Get a random [Concept]. [queryBuilder] can be used to narrow down the search.
     *
     *  * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     *  * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     *  * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getRandom(queryBuilder: QueryBuilder?): Concept =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Concept] by its URL.
     *
     * @param url the [Concept]'s URL
     */
    override fun getEntityByUrl(url: String): QueryResponse<Concept> = getEntity(url)

    /**
     * Get a list of [Concept]s by their URL.
     *
     * @param url the URL of a list of [Concept]s
     */
    override fun getEntitiesByUrl(url: String): PageableQueryResponse<Concept> = getEntitiesInternal(url)

    /**
     * Get a list of [Concept]s. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Concept> =
        getEntitiesInternal(queryBuilder)

    /**
     * Get a list of [Concept]s by their URL. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param url the URL of a list of [Concept]s
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Concept> =
        getEntitiesInternal(url, queryBuilder)

    /**
     * Get a [Concept] by its OpenAlex ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param id the OpenAlex ID of the [Concept]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Concept =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Concept] by its Microsoft Academic Graph ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param id the Microsoft Academic Graph ID of the [Concept]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Concept =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    /**
     * Get a [Concept] by its Wikidata ID. [queryBuilder] can be used to narrow down the search.
     *
     * * see filter fields in the [documentation](https://docs.openalex.org/api-entities/concepts/filter-concepts)
     * * see search fields in the [documentation](https://docs.openalex.org/api-entities/concepts/search-concepts)
     * * see group by fields in the [documentation](https://docs.openalex.org/api-entities/concepts/group-concepts)
     *
     * @param id the Wikidata ID of the [Concept]
     * @param queryBuilder to add search, filter, sort, group by terms to the query
     */
    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Concept =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
