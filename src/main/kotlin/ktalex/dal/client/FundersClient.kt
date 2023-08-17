package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Funder

class FundersClient : BaseClient<Funder>() {

    override val entityType = "funders"

    override fun getRandom(queryBuilder: QueryBuilder?): Funder =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResponse<Funder> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}