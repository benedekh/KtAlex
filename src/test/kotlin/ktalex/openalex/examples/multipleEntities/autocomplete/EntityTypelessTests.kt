package ktalex.openalex.examples.multipleEntities.autocomplete

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.SourceClient

/**
 * https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/autocomplete-entities
 */
class EntityTypelessTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get a entity with ORCID ID https://orcid.org/0000-0002-7436-3176") {
        val searchTerm = "https://orcid.org/0000-0002-7436-3176"
        val response = client.autocomplete(searchTerm)
        response.shouldNotBeNull()
        response.results.shouldBeEmpty()
    }
})
