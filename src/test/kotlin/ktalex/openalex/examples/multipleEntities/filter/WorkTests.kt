package ktalex.openalex.examples.multipleEntities.filter

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder
import java.time.LocalDate

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/filter-entity-lists)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get the works whose type is book") {
        val type = "book"
        val response = client.getEntities(QueryBuilder().eq("type", type))
        response.shouldNotBeNull()

        response.meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.forEach {
            it.type.shouldBe(type)
        }
    }

    should("Get all works published between 2022-01-01 and 2022-01-26 (inclusive)") {
        val expectedIds = listOf(
            "https://openalex.org/W4206841660",
            "https://openalex.org/W4225529913",
            "https://openalex.org/W4200026682",
            "https://openalex.org/W4225691034",
            "https://openalex.org/W4205765055",
            "https://openalex.org/W3112127769",
            "https://openalex.org/W4226192094",
            "https://openalex.org/W4205972999",
            "https://openalex.org/W3217661335",
            "https://openalex.org/W4206975086",
            "https://openalex.org/W4205379795",
            "https://openalex.org/W279337373",
            "https://openalex.org/W2596941707",
            "https://openalex.org/W4225394054",
            "https://openalex.org/W4226181138",
            "https://openalex.org/W4210520398",
            "https://openalex.org/W4206950245",
            "https://openalex.org/W4226087845",
            "https://openalex.org/W594357522",
            "https://openalex.org/W3011667710",
            "https://openalex.org/W3203586449",
            "https://openalex.org/W4360598244",
            "https://openalex.org/W4205170082",
            "https://openalex.org/W3208645186",
            "https://openalex.org/W4226391429"
        )
        val expectedCount = 1765805
        val expectedSize = 25
        val response =
            client.getEntities(
                QueryBuilder().gt("publicationDate", LocalDate.of(2022, 1, 1))
                    .lt("publicationDate", LocalDate.of(2022, 1, 26))
            )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.perPage.shouldBe(expectedSize)
        meta.count.shouldBe(expectedCount)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedSize)

        expectedIds.forEach { expectedId ->
            results.first { it.id!!.url == expectedId }
        }
    }

    should("Get all works that have been cited more than once and are free to read") {
        val minCitations = 1
        val response = client.getEntities(
            QueryBuilder().gt("citedByCount", minCitations).eq("isOa", true)
        )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()

        results.forEach {
            it.citedByCount!!.shouldBeGreaterThan(minCitations)
            it.isOa?.shouldBe(true)
        }
    }

    should("Get all the works that have an author from France AND an author from the UK") {
        val expectedIds = listOf(
            "https://openalex.org/W1757407923",
            "https://openalex.org/W3118615836",
            "https://openalex.org/W2128158076",
            "https://openalex.org/W1981989535",
            "https://openalex.org/W2280404143",
            "https://openalex.org/W2111211467",
            "https://openalex.org/W2136334331",
            "https://openalex.org/W2097995306",
            "https://openalex.org/W2127322768",
            "https://openalex.org/W843007777",
            "https://openalex.org/W2123343298",
            "https://openalex.org/W2126525177",
            "https://openalex.org/W2110052313",
            "https://openalex.org/W4249751050",
            "https://openalex.org/W2102902846",
            "https://openalex.org/W2125826054",
            "https://openalex.org/W2970684805",
            "https://openalex.org/W2024649846",
            "https://openalex.org/W2142841029",
            "https://openalex.org/W2151207643",
            "https://openalex.org/W4292875581",
            "https://openalex.org/W2135625048",
            "https://openalex.org/W2152061559",
            "https://openalex.org/W1978956894",
            "https://openalex.org/W2531269403"
        )
        val expectedSize = 25
        val response = client.getEntities(
            QueryBuilder().eq("institutions.countryCode", "fr").eq("institutions.countryCode", "gb")
        )
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

    should("Get all the works that have an author from France OR an author from the UK") {
        val expectedIds = listOf(
            "https://openalex.org/W2100837269",
            "https://openalex.org/W2194775991",
            "https://openalex.org/W1979290264",
            "https://openalex.org/W2138270253",
            "https://openalex.org/W2889646458",
            "https://openalex.org/W2058122340",
            "https://openalex.org/W2125435699",
            "https://openalex.org/W4294215472",
            "https://openalex.org/W2108234281",
            "https://openalex.org/W2030976617",
            "https://openalex.org/W2015795623",
            "https://openalex.org/W2167279371",
            "https://openalex.org/W2157823046",
            "https://openalex.org/W2103441770",
            "https://openalex.org/W3128646645",
            "https://openalex.org/W2156098321",
            "https://openalex.org/W2097382368",
            "https://openalex.org/W2166281097",
            "https://openalex.org/W2106787323",
            "https://openalex.org/W2014935324",
            "https://openalex.org/W2063450941",
            "https://openalex.org/W2160234571",
            "https://openalex.org/W2098126593",
            "https://openalex.org/W2144081223",
            "https://openalex.org/W2126930838"
        )
        val expectedSize = 25
        val response = client.getEntities(
            QueryBuilder().or("institutions.countryCode", negate = false, "fr", "gb")
        )
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

    should("Get all works that have concepts \"Medicine\" and \"Artificial Intelligence\"") {
        val medicine = "C71924100"
        val artificialIntelligence = "C154945302"
        val response = client.getEntities(
            QueryBuilder().eq("concepts.id", medicine).eq("concepts.id", artificialIntelligence)
        )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()

        results.forEach { result ->
            result.concepts!!.first { it.id!!.id == medicine || it.id!!.id == artificialIntelligence }
        }
    }

    should("Get all works with DOI 10.1371/journal.pone.0266781 or with DOI 10.1371/journal.pone.0267149") {
        val dois =
            listOf("https://doi.org/10.1371/journal.pone.0266781", "https://doi.org/10.1371/journal.pone.0267149")
        val response = client.getEntities(QueryBuilder().or("doi", negate = false, dois[0], dois[1]))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count.shouldBe(2)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(2)

        dois.forEach { doi ->
            results.first { it.doi == doi }
        }
    }
})
