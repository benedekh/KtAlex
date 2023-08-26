package ktalex.openalex.examples.multipleEntities.filter.regions

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/regions)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get the works whose institution is in Global South (see OpenAlex documentation)") {
        val expectedIds = listOf(
            "https://openalex.org/W1979544533",
            "https://openalex.org/W2108234281",
            "https://openalex.org/W2024060531",
            "https://openalex.org/W2152207030",
            "https://openalex.org/W3001118548",
            "https://openalex.org/W2126105956",
            "https://openalex.org/W2161633633",
            "https://openalex.org/W1757407923",
            "https://openalex.org/W2799524357",
            "https://openalex.org/W3008827533",
            "https://openalex.org/W2141260882",
            "https://openalex.org/W2963446712",
            "https://openalex.org/W3009885589",
            "https://openalex.org/W1974907033",
            "https://openalex.org/W3118615836",
            "https://openalex.org/W2128158076",
            "https://openalex.org/W2132525235",
            "https://openalex.org/W639708223",
            "https://openalex.org/W2588681363",
            "https://openalex.org/W2035618305",
            "https://openalex.org/W2112776483",
            "https://openalex.org/W3165656738",
            "https://openalex.org/W2165292984",
            "https://openalex.org/W2746485780",
            "https://openalex.org/W3001897055"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("institutions.is_global_south", true))
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

    should("Get the works grouped is Global South (see OpenAlex documentation)") {
        val expectedCount = 2
        val response = client.getEntities(QueryBuilder().groupBy("institutions.is_global_south"))
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

            it.key!!.shouldBeEqual(it.keyDisplayName!!)
            it.count!!.shouldBePositive()
        }
    }
})
