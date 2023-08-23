package ktalex.openalex.examples.multipleEntities.filter.regions

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.AuthorClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldNotBeNegative

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/regions)
 */
class AuthorTests : ShouldSpec({
    lateinit var client: AuthorClient

    beforeTest {
        client = AuthorClient()
    }
    afterTest {
        client.close()
    }

    should("Get authors whose last know institution is from Global South (see OpenAlex documentation)") {
        val expectedIds = listOf(
            "https://openalex.org/A5053780153",
            "https://openalex.org/A5032245741",
            "https://openalex.org/A5001978013",
            "https://openalex.org/A5032635209",
            "https://openalex.org/A5027475930",
            "https://openalex.org/A5046597133",
            "https://openalex.org/A5025879011",
            "https://openalex.org/A5012278873",
            "https://openalex.org/A5027835055",
            "https://openalex.org/A5068607867",
            "https://openalex.org/A5066716873",
            "https://openalex.org/A5000432967",
            "https://openalex.org/A5003642180",
            "https://openalex.org/A5060002817",
            "https://openalex.org/A5049692788",
            "https://openalex.org/A5023363049",
            "https://openalex.org/A5073216396",
            "https://openalex.org/A5037677450",
            "https://openalex.org/A5064842058",
            "https://openalex.org/A5076699095",
            "https://openalex.org/A5086664284",
            "https://openalex.org/A5012677271",
            "https://openalex.org/A5071773009",
            "https://openalex.org/A5042241049",
            "https://openalex.org/A5071037763"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("lastKnownInstitution.isGlobalSouth", true))
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

    should(
        "Get number of authors with last known institution in the Global South, by country " +
            "(see OpenAlex documentation)"
    ) {
        val response = client.getEntities(
            QueryBuilder()
                .eq("lastKnownInstitution.isGlobalSouth", true)
                .groupBy("lastKnownInstitution.countryCode")
        )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()

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

            it.count!!.shouldNotBeNegative()
        }
    }
})
