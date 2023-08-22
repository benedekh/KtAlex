package ktalex.openalex.examples.multipleEntities.groupBy

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.ConceptClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-groups-of-entities)
 */
class ConceptTests : ShouldSpec({
    lateinit var client: ConceptClient

    beforeTest {
        client = ConceptClient()
    }
    afterTest {
        client.close()
    }

    should("Group concepts by level") {
        val response = client.getEntities(QueryBuilder().groupBy("level"))
        response.shouldNotBeNull()

        val meta = response.meta
        meta.shouldNotBeNull()

        response.results.shouldBeEmpty()

        val groupBy = response.groupBy
        groupBy.shouldNotBeNull()

        // should contain level 3
        groupBy.first { it.key == "3" && it.keyDisplayName == "3" }

        groupBy.forEach {
            it.key.shouldBeSet()
            it.keyDisplayName.shouldBeSet()
            it.key!!.shouldBeEqual(it.keyDisplayName!!)
            it.count!!.shouldBeInRange(IntRange(0, Int.MAX_VALUE))
        }
    }
})
