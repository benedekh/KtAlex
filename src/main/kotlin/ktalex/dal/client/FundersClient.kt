package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Funder

class FundersClient : BaseEntityClient<Funder>() {

    override val entityType = "funders"

    override fun getRandom(queryBuilder: QueryBuilder?): Funder =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Funder? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}