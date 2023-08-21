package ktalex.openalex.examples.singleEntity.random

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import ktalex.dal.client.ConceptClient
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-single-entities/random-result)
 */
class ConceptTests : ShouldSpec({
    lateinit var client: ConceptClient

    beforeTest {
        client = ConceptClient()
    }
    afterTest {
        client.close()
    }

    should("Get a random concept") {
        val result = client.getRandom()
        result.shouldNotBeNull()

        result.should {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.url.shouldBeSet()
            it.id!!.id.shouldBeSet()
        }
    }
})
