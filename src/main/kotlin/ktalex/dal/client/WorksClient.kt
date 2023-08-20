package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Work

class WorksClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Work>(openAlexBaseUrl, mailTo) {

    override val entityType = "works"

    override fun getRandom(queryBuilder: QueryBuilder?): Work =
        getEntity("$baseUrl/random${queryBuilder?.build().orEmpty()}")

    override fun getEntityWithExactType(url: String): QueryResponse<Work> = getEntity(url)

    override fun getEntities(url: String): PageableQueryResponse<Work> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Work> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByDoi(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build().orEmpty()}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build().orEmpty()}")

    fun getByPubMedId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/pmid:$id${queryBuilder?.build().orEmpty()}")
}
