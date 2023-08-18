package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Institution

class InstitutionsClient : BaseEntityClient<Institution>() {

    override val entityType = "institutions"

    override fun getRandom(queryBuilder: QueryBuilder?): Institution =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(url: String): QueryResponse<Institution> = getEntity(url)!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}r")

    fun getByRorId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/ror:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Institution? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}