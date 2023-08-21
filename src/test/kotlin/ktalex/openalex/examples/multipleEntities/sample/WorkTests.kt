package ktalex.openalex.examples.multipleEntities.sample

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/sample-entity-lists)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get 100 random works") {
        val pageSize = 100
        val response = client.getEntities(QueryBuilder().sampling(100).pagination(perPage = pageSize))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.perPage!!.shouldBeEqual(pageSize)
        meta.count!!.shouldBeInRange(IntRange(pageSize, Int.MAX_VALUE))

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBeEqual(pageSize)
    }

    should("Get 50 random works that are open access and published in 2021") {
        val pageSize = 50
        val publicationYear = 2021
        val response = client.getEntities(
            QueryBuilder()
                .sampling(50)
                .pagination(perPage = pageSize)
                .eq("openAccess.isOa", true)
                .eq("publicationYear", publicationYear)
        )
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.perPage!!.shouldBeEqual(pageSize)
        meta.count!!.shouldBeInRange(IntRange(pageSize, Int.MAX_VALUE))

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBeEqual(pageSize)

        results.forEach {
            it.openAccess!!.isOa!!.shouldBeTrue()
            it.publicationYear!!.shouldBeEqual(publicationYear)
        }
    }
})
