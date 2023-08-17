package ktalex.dal

import ktalex.dal.query.QueryBuilder
import ktalex.model.QueryResults
import ktalex.model.Work

class WorksClient : BaseClient<Work>() {

    override val baseUrl = "$openAlexBaseUrl/works"

    override fun getRandom(queryBuilder: QueryBuilder?): Work =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResults<Work> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByDoi(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByPubMedId(id: String, queryBuilder: QueryBuilder? = null): Work? =
        getEntity("$baseUrl/pmid:$id${queryBuilder?.build() ?: ""}")

}