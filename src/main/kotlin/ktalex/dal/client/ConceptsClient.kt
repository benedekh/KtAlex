package ktalex.dal.client

import ktalex.dal.query.QueryBuilder
import ktalex.model.Concept
import ktalex.dal.query.QueryResponse

class ConceptsClient : BaseClient<Concept>() {

    override val entityType = "concepts"

    override fun getRandom(queryBuilder: QueryBuilder?): Concept =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntities(queryBuilder: QueryBuilder?): QueryResponse<Concept> =
        getEntity("$baseUrl${queryBuilder?.build() ?: ""}")!!

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")

}