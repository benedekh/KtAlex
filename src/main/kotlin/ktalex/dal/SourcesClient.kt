package ktalex.dal

import ktalex.dal.query.QueryBuilder
import ktalex.model.QueryResults
import ktalex.model.Source

class SourcesClient : BaseClient<Source>() {

    override val baseUrl = "$openAlexBaseUrl/sources"

    override fun getRandom(queryBuilder: QueryBuilder?): Source =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResults<Source> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByIssn(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/issn:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Source? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}