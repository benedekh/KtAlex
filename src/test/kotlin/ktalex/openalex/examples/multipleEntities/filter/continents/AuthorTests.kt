package ktalex.openalex.examples.multipleEntities.filter.continents

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.AuthorClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/continents)
 */
class AuthorTests : ShouldSpec({
    lateinit var client: AuthorClient

    beforeTest {
        client = AuthorClient()
    }
    afterTest {
        client.close()
    }

    should("Get authors whose last know institution is in Oceania") {
        val expectedIds = listOf(
            "https://openalex.org/A5006901857",
            "https://openalex.org/A5086797953",
            "https://openalex.org/A5042664825",
            "https://openalex.org/A5001330715",
            "https://openalex.org/A5052804718",
            "https://openalex.org/A5058811939",
            "https://openalex.org/A5021374155",
            "https://openalex.org/A5008340237",
            "https://openalex.org/A5071088289",
            "https://openalex.org/A5013507311",
            "https://openalex.org/A5052022256",
            "https://openalex.org/A5007613197",
            "https://openalex.org/A5026175078",
            "https://openalex.org/A5066851759",
            "https://openalex.org/A5035594164",
            "https://openalex.org/A5014592284",
            "https://openalex.org/A5066927852",
            "https://openalex.org/A5023574266",
            "https://openalex.org/A5011216396",
            "https://openalex.org/A5011264157",
            "https://openalex.org/A5082369913",
            "https://openalex.org/A5080921373",
            "https://openalex.org/A5066364056",
            "https://openalex.org/A5013442821",
            "https://openalex.org/A5019133187"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("lastKnownInstitution.continent", "oceania"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.perPage.shouldBe(expectedSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedSize)

        expectedIds.forEach { expectedId ->
            results.first { it.id!!.url == expectedId }
        }
    }

    should("Get authors grouped by continent") {
        val expectedCount = 8
        val response = client.getEntities(QueryBuilder().groupBy("lastKnownInstitution.continent"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count.shouldBe(expectedCount)

        response.results.shouldBeEmpty()

        val groupBys = response.groupBy
        groupBys.shouldNotBeNull()
        groupBys.forEach {
            it.key.shouldNotBeNull()
            it.keyDisplayName.shouldNotBeNull()
            it.count.shouldNotBeNull()

            if (it.key == "unknown") {
                it.key!!.shouldBeEqual(it.keyDisplayName!!)
            } else {
                it.key!!.shouldNotBeEqual(it.keyDisplayName!!)
            }

            if (it.keyDisplayName != "Antarctica") {
                it.count!!.shouldBePositive()
            } else {
                it.count!!.shouldBe(0)
            }
        }
    }
})
