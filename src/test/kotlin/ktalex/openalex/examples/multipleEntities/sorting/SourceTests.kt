package ktalex.openalex.examples.multipleEntities.sorting

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.SourceClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSorted

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/sort-entity-lists)
 */
class SourceTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get all sources, in alphabetical order by title") {
        val expectedResultsSize = 25
        val response = client.getEntities(QueryBuilder().sort("displayName"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count?.shouldBePositive()
        meta.perPage?.shouldBe(expectedResultsSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedResultsSize)

        results.map { it.displayName!! }.shouldBeSorted()
    }
})
