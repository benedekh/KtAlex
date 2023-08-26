package ktalex.openalex.examples.multipleEntities.filter.continents

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.InstitutionClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/continents)
 */
class InstitutionTests : ShouldSpec({
    lateinit var client: InstitutionClient

    beforeTest {
        client = InstitutionClient()
    }
    afterTest {
        client.close()
    }

    should("Get institutions from Oceania") {
        val expectedIds = listOf(
            "https://openalex.org/I129604602",
            "https://openalex.org/I165779595",
            "https://openalex.org/I165143802",
            "https://openalex.org/I31746571",
            "https://openalex.org/I56590836",
            "https://openalex.org/I118347636",
            "https://openalex.org/I177877127",
            "https://openalex.org/I5681781",
            "https://openalex.org/I154130895",
            "https://openalex.org/I1292875679",
            "https://openalex.org/I160993911",
            "https://openalex.org/I80281795",
            "https://openalex.org/I99043593",
            "https://openalex.org/I11701301",
            "https://openalex.org/I114017466",
            "https://openalex.org/I149704539",
            "https://openalex.org/I205640436",
            "https://openalex.org/I204824540",
            "https://openalex.org/I196829312",
            "https://openalex.org/I78757542",
            "https://openalex.org/I129801699",
            "https://openalex.org/I82951845",
            "https://openalex.org/I169541294",
            "https://openalex.org/I170239107",
            "https://openalex.org/I51158804"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("continent", "oceania"))
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

    should("Get institutions grouped by continent") {
        val expectedCount = 8
        val response = client.getEntities(QueryBuilder().groupBy("continent"))
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
