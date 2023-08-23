package ktalex.openalex.examples.multipleEntities.search

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.AuthorClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet
import ktalex.utils.shouldBeSorted

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/search-entities)
 */
class AuthorTests : ShouldSpec({
    lateinit var client: AuthorClient

    beforeTest {
        client = AuthorClient()
    }
    afterTest {
        client.close()
    }

    should("Get authors who have \"Einstein\" as part of their name") {
        val searchTerm = "einstein"
        val response = client.getEntities(QueryBuilder().search("displayName", searchTerm))
        response.shouldNotBeNull()

        response.meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.forEach {
            it.displayName.shouldBeSet()
            it.displayName!!.lowercase().shouldContain(searchTerm)
        }

        results.map { it.relevanceScore ?: Float.MAX_VALUE }.shouldBeSorted(isDescending = true)
    }
})
