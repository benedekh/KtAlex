package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Institution

class InstitutionsClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Institution>(openAlexBaseUrl, mailTo) {

    override val entityType = "institutions"

    override fun getRandom(queryBuilder: QueryBuilder?): Institution =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Institution> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Institution> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Institution> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Institution> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}r")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build().orEmpty()}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build().orEmpty()}")
}
