package ktalex.openalex.examples.multipleEntities.autocomplete

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.WorkClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

/**
 * https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get greenhou papers from 2010") {
        val autocompleteTerm = "greenhou"
        val searchTerm = "frogs"
        val publicationYear = 2010
        val response = client.autocomplete(
            autocompleteTerm,
            QueryBuilder().search(searchTerm).eq("publicationYear", publicationYear)
        )
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.size.shouldBe(6)

        results.forEach {
            it.displayName.shouldBeSet()
            it.displayName!!.lowercase().shouldContain(autocompleteTerm)
        }
    }
})
