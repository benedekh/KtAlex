package ktalex.dal.client

import ktalex.dal.query.PageableQueryResponse
import ktalex.dal.query.QueryBuilder
import ktalex.dal.query.QueryResponse
import ktalex.model.Concept

class ConceptsClient(openAlexBaseUrl: String? = null, mailTo: String? = null) :
    BaseEntityClient<Concept>(openAlexBaseUrl, mailTo) {

    override val entityType = "concepts"

    override fun getRandom(queryBuilder: QueryBuilder?): Concept =
        getEntity("$baseUrl/random${queryBuilder?.build() ?: ""}")!!

    override fun getEntityWithExactType(url: String): QueryResponse<Concept> = getEntity(url)!!

    override fun getEntities(url: String): PageableQueryResponse<Concept> = getEntitiesInternal(url)

    override fun getEntities(queryBuilder: QueryBuilder?): PageableQueryResponse<Concept> = getEntitiesInternal()

    override fun getEntities(url: String, queryBuilder: QueryBuilder?): PageableQueryResponse<Concept> =
        getEntitiesInternal(url, queryBuilder)

    fun getByOpenAlexId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/$id${queryBuilder?.build() ?: ""}")

    fun getByMicrosoftAcademicGraphId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/mag:$id${queryBuilder?.build() ?: ""}")

    fun getByWikidataId(id: String, queryBuilder: QueryBuilder? = null): Concept? =
        getEntity("$baseUrl/wikidata:$id${queryBuilder?.build() ?: ""}")
}
