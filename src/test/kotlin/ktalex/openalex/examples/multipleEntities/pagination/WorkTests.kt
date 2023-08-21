package ktalex.openalex.examples.multipleEntities.pagination

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import ktalex.dal.client.WorksClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/paging)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorksClient

    beforeTest {
        client = WorksClient()
    }
    afterTest {
        client.close()
    }

    should("Return the second page of a list of works") {
        val pageIndex = 2
        val response = client.getEntities(QueryBuilder().pagination(page = pageIndex))

        val results = response.results
        results.shouldNotBeNull()
        val resultsSize = results.size
        resultsSize.shouldBePositive()

        // get a random item from the list
        val randomResult = results[Random.Default.nextInt(0..<resultsSize)]
        randomResult.shouldNotBeNull()
        randomResult.displayName.shouldBeSet()
        randomResult.id.shouldNotBeNull()
        randomResult.id!!.id.shouldBeSet()
        randomResult.id!!.url.shouldBeSet()

        response.meta.shouldNotBeNull()
        response.meta!!.should {
            it.page!!.shouldBeEqual(pageIndex)
            it.perPage!!.shouldBeEqual(resultsSize)
            it.count!!.shouldBeInRange(resultsSize..Int.MAX_VALUE)
            it.dbResponseTimeMs!!.shouldBePositive()
            it.nextCursor.shouldBeNull()
        }
    }

    should("Return the second page with 200 entries of works") {
        val pageIndex = 2
        val pageSize = 200
        val response = client.getEntities(QueryBuilder().pagination(page = pageIndex, perPage = pageSize))

        val results = response.results
        results.shouldNotBeNull()
        val resultsSize = results.size
        resultsSize.shouldBeEqual(pageSize)

        // get a random item from the list
        val randomResult = results[Random.Default.nextInt(0..<resultsSize)]
        randomResult.shouldNotBeNull()
        randomResult.displayName.shouldBeSet()
        randomResult.id.shouldNotBeNull()
        randomResult.id!!.id.shouldBeSet()
        randomResult.id!!.url.shouldBeSet()

        response.meta.shouldNotBeNull()
        response.meta!!.should {
            it.page!!.shouldBeEqual(pageIndex)
            it.perPage!!.shouldBeEqual(resultsSize)
            it.count!!.shouldBeInRange(resultsSize..Int.MAX_VALUE)
            it.dbResponseTimeMs!!.shouldBePositive()
            it.nextCursor.shouldBeNull()
        }
    }

    should("Return a page with 100 entries of works and nextCursor set") {
        val publicationYear = 2020
        val pageSize = 200
        val response = client.getEntities(
            QueryBuilder()
                .pagination(cursor = "*", perPage = pageSize)
                .eq("publicationYear", publicationYear)
        )

        val results = response.results
        results.shouldNotBeNull()
        val resultsSize = results.size
        resultsSize.shouldBeEqual(pageSize)

        // publication year must be 2020
        results.forEach {
            it.publicationYear!!.shouldBeEqual(publicationYear)
        }

        // get a random item from the list
        val randomResult = results[Random.Default.nextInt(0..<resultsSize)]
        randomResult.shouldNotBeNull()
        randomResult.displayName.shouldBeSet()
        randomResult.id.shouldNotBeNull()
        randomResult.id!!.id.shouldBeSet()
        randomResult.id!!.url.shouldBeSet()

        response.meta.shouldNotBeNull()
        response.meta!!.should {
            it.nextCursor.shouldBeSet()
            it.perPage!!.shouldBeEqual(resultsSize)
            it.count!!.shouldBeInRange(resultsSize..Int.MAX_VALUE)
            it.dbResponseTimeMs!!.shouldBePositive()
            it.page.shouldBeNull()
        }
    }

    should("Return the next page with 100 entries of works and nextCursor set") {
        val publicationYear = 2020
        val pageSize = 200
        val firstPage = client.getEntities(
            QueryBuilder()
                .pagination(cursor = "*", perPage = pageSize)
                .eq("publicationYear", publicationYear)
        )
        // get the second page
        val response = firstPage.nextPage()
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        val resultsSize = results.size
        resultsSize.shouldBeEqual(pageSize)

        // publication year must be 2020
        results.forEach {
            it.publicationYear!!.shouldBeEqual(publicationYear)
        }

        // get a random item from the list
        val randomResult = results[Random.Default.nextInt(0..<resultsSize)]
        randomResult.shouldNotBeNull()
        randomResult.displayName.shouldBeSet()
        randomResult.id.shouldNotBeNull()
        randomResult.id!!.id.shouldBeSet()
        randomResult.id!!.url.shouldBeSet()

        response.meta.shouldNotBeNull()
        response.meta!!.should {
            it.nextCursor.shouldBeSet()
            it.perPage!!.shouldBeEqual(resultsSize)
            it.count!!.shouldBeInRange(resultsSize..Int.MAX_VALUE)
            it.dbResponseTimeMs!!.shouldBePositive()
            it.page.shouldBeNull()
        }
    }
})
