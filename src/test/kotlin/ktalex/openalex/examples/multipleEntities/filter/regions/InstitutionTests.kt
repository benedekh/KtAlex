package ktalex.openalex.examples.multipleEntities.filter.regions

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.InstitutionClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/geo/regions)
 */
class InstitutionTests : ShouldSpec({
    lateinit var client: InstitutionClient

    beforeTest {
        client = InstitutionClient()
    }
    afterTest {
        client.close()
    }

    should("Get institutions that are in Global South (see OpenAlex documentation)") {
        val expectedIds = listOf(
            "https://openalex.org/I19820366",
            "https://openalex.org/I17974374",
            "https://openalex.org/I99065089",
            "https://openalex.org/I76130692",
            "https://openalex.org/I183067930",
            "https://openalex.org/I4210165038",
            "https://openalex.org/I165932596",
            "https://openalex.org/I20231570",
            "https://openalex.org/I47720641",
            "https://openalex.org/I889458895",
            "https://openalex.org/I204983213",
            "https://openalex.org/I24943067",
            "https://openalex.org/I879563668",
            "https://openalex.org/I177725633",
            "https://openalex.org/I172675005",
            "https://openalex.org/I181391015",
            "https://openalex.org/I87445476",
            "https://openalex.org/I126520041",
            "https://openalex.org/I24185976",
            "https://openalex.org/I154099455",
            "https://openalex.org/I881766915",
            "https://openalex.org/I37461747",
            "https://openalex.org/I157773358",
            "https://openalex.org/I116953780",
            "https://openalex.org/I151201029"
        )
        val expectedSize = 25
        expectedIds.size.shouldBe(expectedSize)

        val response = client.getEntities(QueryBuilder().eq("isGlobalSouth", true))
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

    should("Get institutions grouped by is Global South (see OpenAlex documentation)") {
        val expectedCount = 2
        val response = client.getEntities(QueryBuilder().groupBy("isGlobalSouth"))
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
