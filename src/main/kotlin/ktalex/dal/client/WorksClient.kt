package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Work

class WorksClient : BaseEntityClient<Work>() {

    override val entityType = "works"

    override fun getRandom(queryBuilder: QueryBuilder?): Work =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(url: String): QueryResponse<Work> = getEntity(url)!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByDoi(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByPubMedId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/pmid:$id${queryBuilder?.build() ?: ""}")

}