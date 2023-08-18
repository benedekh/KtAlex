package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.model.Source

class SourcesClient : BaseEntityClient<Source>() {

    override val entityType = "sources"

    override fun getRandom(queryBuilder: QueryBuilder?): Source =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(url: String): PageableQueryResponse<Source> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(queryBuilder)

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Source> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByIssn(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/issn:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}