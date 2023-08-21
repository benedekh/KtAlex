package ktalex.openalex.examples.multipleEntities.autocomplete

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.SourceClient

/**
 * https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities
 */
class SourceTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get a list of random sources containing S49861241") {
        val searchTerm = "S49861241"
        val response = client.autocomplete(searchTerm)
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(1)

        val id = results.first().id
        id.shouldNotBeNull()

        id.id.shouldBeEqual(searchTerm)
        id.url.shouldContain(searchTerm)
    }
})
