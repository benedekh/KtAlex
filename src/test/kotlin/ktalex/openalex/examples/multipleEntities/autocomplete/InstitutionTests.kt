package ktalex.openalex.examples.multipleEntities.autocomplete

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.InstitutionClient
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities)
 */
class InstitutionTests : ShouldSpec({
    lateinit var client: InstitutionClient

    beforeTest {
        client = InstitutionClient()
    }
    afterTest {
        client.close()
    }

    should("Get a list of random institutions containing flori") {
        val searchTerm = "flori"
        val response = client.autocomplete(searchTerm)
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()

        results.forEach {
            it.displayName.shouldBeSet()
            it.displayName!!.lowercase().shouldContain(searchTerm)
        }
    }

    should("Get a list of random institutions containing Florida") {
        val searchTerm = "Florida"
        val response = client.autocomplete(searchTerm)
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()

        results.forEach {
            it.displayName.shouldBeSet()
            it.displayName!!.shouldContain(searchTerm)
        }
    }
})
