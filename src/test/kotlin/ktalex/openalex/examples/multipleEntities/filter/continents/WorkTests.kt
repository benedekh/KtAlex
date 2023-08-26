package ktalex.openalex.examples.multipleEntities.filter.continents

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/continents)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get the works whose institution is in Oceania") {
        val expectedIds = listOf(
            "https://openalex.org/W1979290264",
            "https://openalex.org/W2114104545",
            "https://openalex.org/W2072970694",
            "https://openalex.org/W2146512944",
            "https://openalex.org/W3118615836",
            "https://openalex.org/W2138664283",
            "https://openalex.org/W2133990480",
            "https://openalex.org/W2112776483",
            "https://openalex.org/W2183341477",
            "https://openalex.org/W2161374186",
            "https://openalex.org/W1513618424",
            "https://openalex.org/W2280404143",
            "https://openalex.org/W2073832139",
            "https://openalex.org/W2128880918",
            "https://openalex.org/W2020267609",
            "https://openalex.org/W2171129594",
            "https://openalex.org/W1853767801",
            "https://openalex.org/W2111211467",
            "https://openalex.org/W2138207763",
            "https://openalex.org/W3103145119",
            "https://openalex.org/W197066864",
            "https://openalex.org/W2138522501",
            "https://openalex.org/W2272984102",
            "https://openalex.org/W2115786064",
            "https://openalex.org/W1866311589"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("institutions.continent", "oceania"))
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

    should("Get the works grouped by continent") {
        val expectedCount = 8
        val response = client.getEntities(QueryBuilder().groupBy("institutions.continent"))
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
