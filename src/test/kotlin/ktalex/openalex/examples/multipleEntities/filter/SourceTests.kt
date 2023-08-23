package ktalex.openalex.examples.multipleEntities.filter

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.SourceClient
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/filter-entity-lists)
 */
class SourceTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get sources that host more than 1000 works") {
        val minWorksCount = 1000
        val response = client.getEntities(QueryBuilder().gt("worksCount", minWorksCount))
        response.shouldNotBeNull()

        response.meta.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.forEach {
            it.worksCount!!.shouldBeGreaterThan(minWorksCount)
        }
    }
})
