package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Publisher
import ktalex.dal.query.QueryResponse

class PublishersClient : BaseClient<Publisher>() {

    override val entityType = "publishers"

    override fun getRandom(queryBuilder: QueryBuilder?): Publisher =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResponse<Publisher> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Publisher? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Publisher? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Publisher? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}