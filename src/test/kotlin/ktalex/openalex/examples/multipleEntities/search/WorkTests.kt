package ktalex.openalex.examples.multipleEntities.search

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet
import ktalex.utils.shouldBeSorted

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/search-entities)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get works with search term \"dna\" in the title, abstract, or fulltext:") {
        val searchTerm = "dna"
        val response = client.getEntities(QueryBuilder().search(searchTerm))
        response.shouldNotBeNull()

        response.meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.forEach {
            it.title.shouldBeSet()
            it.title!!.lowercase().shouldContain(searchTerm)
        }

        results.map { it.relevanceScore ?: Float.MAX_VALUE }.shouldBeSorted(isDescending = true)
    }

    should("Get works that mention \"elmo\" and \"sesame street,\" but not the words \"cookie\" or \"monster\"") {
        val expectedIds = listOf(
            "https://openalex.org/W2113909876",
            "https://openalex.org/W1674866864",
            "https://openalex.org/W1614984768",
            "https://openalex.org/W2130312682",
            "https://openalex.org/W2147637953",
            "https://openalex.org/W2117714514",
            "https://openalex.org/W2109520718",
            "https://openalex.org/W2010935941",
            "https://openalex.org/W2171467937",
            "https://openalex.org/W2120452741",
            "https://openalex.org/W1971775660",
            "https://openalex.org/W1981451349",
            "https://openalex.org/W2118706305",
            "https://openalex.org/W2009853848",
            "https://openalex.org/W2083791919",
            "https://openalex.org/W1578948057",
            "https://openalex.org/W1753640254",
            "https://openalex.org/W2008519768",
            "https://openalex.org/W2604833636",
            "https://openalex.org/W2053497327",
            "https://openalex.org/W2170940508",
            "https://openalex.org/W1971552587",
            "https://openalex.org/W2042235680",
            "https://openalex.org/W2141685861",
            "https://openalex.org/W1997219967"
        )
        val expectedSize = 25
        val response =
            client.getEntities(QueryBuilder().search("\"elmo\" AND \"sesame street\" NOT (cookie OR monster)"))
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

        results.map { it.relevanceScore!! }.shouldBeSorted(isDescending = true)
    }

    should(
        "Get works with the exact phrase \"fierce creatures\" in the title or abstract " +
            "(returns just a few results)"
    ) {
        val expectedIds = listOf(
            "https://openalex.org/W2049577344",
            "https://openalex.org/W2207755691",
            "https://openalex.org/W561890423",
            "https://openalex.org/W2463559255",
            "https://openalex.org/W2127320208",
            "https://openalex.org/W2092938573",
            "https://openalex.org/W106071034",
            "https://openalex.org/W2029136907",
            "https://openalex.org/W2041676106",
            "https://openalex.org/W2137980467",
            "https://openalex.org/W3152133111",
            "https://openalex.org/W2047096407",
            "https://openalex.org/W1976177001",
            "https://openalex.org/W2023379866",
            "https://openalex.org/W1607061301",
            "https://openalex.org/W2038759110",
            "https://openalex.org/W2078912927",
            "https://openalex.org/W2069723713",
            "https://openalex.org/W2563732999",
            "https://openalex.org/W2099368354",
            "https://openalex.org/W1968713111",
            "https://openalex.org/W2048677109",
            "https://openalex.org/W2046621453",
            "https://openalex.org/W1982058715",
            "https://openalex.org/W2054593487"
        )
        val expectedCount = 141
        val expectedSize = 25
        val response = client.getEntities(QueryBuilder().search("\"fierce creatures\""))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count!!.shouldBe(expectedCount)
        meta.perPage.shouldBe(expectedSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedSize)

        expectedIds.forEach { expectedId ->
            results.first { it.id!!.url == expectedId }
        }

        results.map { it.relevanceScore ?: Float.MAX_VALUE }.shouldBeSorted(isDescending = true)
    }

    should(
        "Get works with the words \"fierce\" and \"creatures\" in the title or abstract, with works that have " +
            "the two words close together ranked higher by relevance_score (returns way more results)"
    ) {
        val expectedIds = listOf(
            "https://openalex.org/W2049577344",
            "https://openalex.org/W2207755691",
            "https://openalex.org/W2466650320",
            "https://openalex.org/W1980061023",
            "https://openalex.org/W187591979",
            "https://openalex.org/W2028514556",
            "https://openalex.org/W2233791261",
            "https://openalex.org/W4247285710",
            "https://openalex.org/W2033823029",
            "https://openalex.org/W1965574139",
            "https://openalex.org/W2025494137",
            "https://openalex.org/W2136821570",
            "https://openalex.org/W1987720655",
            "https://openalex.org/W2070488390",
            "https://openalex.org/W1966702838",
            "https://openalex.org/W4240915221",
            "https://openalex.org/W2977589106",
            "https://openalex.org/W561890423",
            "https://openalex.org/W2082423614",
            "https://openalex.org/W2173017428",
            "https://openalex.org/W2153853937",
            "https://openalex.org/W2463559255",
            "https://openalex.org/W2090057077",
            "https://openalex.org/W2127320208",
            "https://openalex.org/W1994173157"
        )
        val expectedCount = 141
        val expectedSize = 25
        val response = client.getEntities(QueryBuilder().search("fierce creatures"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count!!.shouldBeGreaterThan(expectedCount)
        meta.perPage.shouldBe(expectedSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedSize)

        expectedIds.forEach { expectedId ->
            results.first { it.id!!.url == expectedId }
        }

        results.map { it.relevanceScore ?: Float.MAX_VALUE }.shouldBeSorted(isDescending = true)
    }

    should("Get works with \"cubist\" in the title") {
        val expectedSize = 25
        val searchTerm = "cubist"
        val response = client.getEntities(QueryBuilder().search("title", searchTerm))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.perPage.shouldBe(expectedSize)

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(expectedSize)

        results.forEach { result ->
            result.title?.let {
                it.lowercase().shouldContain(searchTerm)
            }
        }

        results.map { it.relevanceScore!! }.shouldBeSorted(isDescending = true)
    }
})
