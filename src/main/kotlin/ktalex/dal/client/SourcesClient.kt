package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Source

class SourcesClient : BaseEntityClient<Source>() {

    override val entityType = "sources"

    override fun getRandom(queryBuilder: QueryBuilder?): Source =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByIssn(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/issn:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}