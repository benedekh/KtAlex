package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Institution
import ktalex.dal.query.QueryResponse

class InstitutionsClient : BaseClient<Institution>() {

    override val entityType = "institutions"

    override fun getRandom(queryBuilder: QueryBuilder?): Institution =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResponse<Institution> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}r")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}