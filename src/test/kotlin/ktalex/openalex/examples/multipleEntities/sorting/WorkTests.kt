package ktalex.openalex.examples.multipleEntities.sorting

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSorted

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/sort-entity-lists)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("All works, sorted by cited_by_count (highest counts first)") {
        val expectedResultsSize = 25
        val response = client.getEntities(QueryBuilder().sort("citedByCount", sortDescending = true))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count?.shouldBePositive()
        meta.perPage?.shouldBe(expectedResultsSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedResultsSize)

        results.map { it.citedByCount!! }.shouldBeSorted(isDescending = true)
    }

    should("Sort by year, then by relevance_score (both in descending order) when searching for \"bioplastics\"") {
        val expectedResultsSize = 25
        val response = client.getEntities(
            QueryBuilder().search("displayName", "bioplastics").sort("publicationYear", sortDescending = true)
                .sort("relevanceScore", sortDescending = true)
        )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count?.shouldBePositive()
        meta.perPage?.shouldBe(expectedResultsSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedResultsSize)

        results.forEach {
            it.displayName!!.lowercase().shouldContain("bioplastic")
        }

        results.filter { it.relevanceScore != null }.map { it.relevanceScore!! }.shouldBeSorted(isDescending = true)
        results.map { it.publicationYear!! }.shouldBeSorted(isDescending = true)
    }
})
