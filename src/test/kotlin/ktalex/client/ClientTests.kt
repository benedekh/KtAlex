package ktalex.client

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import ktalex.dal.client.AuthorClient
import ktalex.dal.query.QueryBuilder
import kotlin.coroutines.cancellation.CancellationException

class ClientTests : ShouldSpec({

    should("Not work if client is closed") {
        val client = AuthorClient()
        val query = QueryBuilder().select("displayName")

        client.getRandom(query)

        // if we close the client then it should not work anymore
        client.close()

        shouldThrow<CancellationException> {
            client.getRandom(query)
        }
    }

    should(
        "Set the email address to get an email from OpenAlex team about the polite pool. See " +
            "https://docs.openalex.org/how-to-use-the-api/rate-limits-and-authentication#the-polite-pool"
    ) {
        AuthorClient(mailTo = "hello@world.com").getRandom().shouldNotBeNull()
    }
})
