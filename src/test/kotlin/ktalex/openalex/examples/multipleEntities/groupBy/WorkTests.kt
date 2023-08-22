package ktalex.openalex.examples.multipleEntities.groupBy

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-groups-of-entities)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get counts of works by Open Access status") {
        val expectedNumberOfGroupBys = 6
        val response = client.getEntities(QueryBuilder().groupBy("oaStatus"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count!!.shouldBeEqual(expectedNumberOfGroupBys)

        response.results.shouldBeEmpty()

        val groupBy = response.groupBy
        groupBy.shouldNotBeNull()
        groupBy.size.shouldBeEqual(expectedNumberOfGroupBys)

        groupBy.forEach {
            it.key.shouldBeSet()
            it.keyDisplayName.shouldBeSet()
            it.key!!.shouldBeEqual(it.keyDisplayName!!)
            it.count!!.shouldBePositive()
        }
    }

    should("Get Harvard University in groupBy result") {
        val expectedNumberOfGroupBys = 200
        val response = client.getEntities(QueryBuilder().groupBy("authorships.institutions.id"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()
        meta.count!!.shouldBeEqual(expectedNumberOfGroupBys)
        meta.perPage!!.shouldBeEqual(expectedNumberOfGroupBys)

        response.results.shouldBeEmpty()

        val groupBy = response.groupBy
        groupBy.shouldNotBeNull()
        groupBy.size.shouldBeEqual(expectedNumberOfGroupBys)

        // should contain Harvard Univeristy
        val harvardId = "https://openalex.org/I136199984"
        val harvardName = "Harvard University"
        groupBy.first { it.key == harvardId && it.keyDisplayName == harvardName }.shouldNotBeNull()

        groupBy.forEach {
            it.key.shouldBeSet()
            it.keyDisplayName.shouldBeSet()
            it.count!!.shouldBePositive()
        }
    }
})
