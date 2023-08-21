package ktalex.openalex.examples.multipleEntities.sample

import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.SourceClient
import ktalex.dal.error.OpenAlexException
import ktalex.dal.query.QueryBuilder

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/sample-entity-lists)
 */
class SourceTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get the same set of random records, in the same order, multiple times") {
        val firstResponse = client.getEntities(QueryBuilder().sampling(20, 123))
        firstResponse.shouldNotBeNull()

        val secondResponse = client.getEntities(QueryBuilder().sampling(20, 123))
        secondResponse.shouldNotBeNull()

        firstResponse.results!!.shouldBeEqual(secondResponse.results!!)
    }

    should("Not sample more than 10 000 records") {
        val response = client.getEntities(QueryBuilder().sampling(10000))
        response.shouldNotBeNull()

        try {
            client.getEntities(QueryBuilder().sampling(10001))
            fail("Should not reach here")
        } catch (ex: OpenAlexException) {
            ex.message.shouldContain("Sample size must be less than or equal to 10,000")
        } catch (t: Throwable) {
            fail("Should have thrown an OpenAlexException")
        }
    }

    should("Cursor pagination is not supported with sampling") {
        try {
            QueryBuilder().sampling(1).pagination(cursor = "*")
            fail("Should not reach here")
        } catch (ex: IllegalArgumentException) {
            ex.message.shouldContain("Cursor cannot be used with sampling")
        } catch (t: Throwable) {
            fail("Should have thrown an IllegalArgumentException")
        }
    }
})
