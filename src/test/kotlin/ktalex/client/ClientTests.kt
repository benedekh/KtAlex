package ktalex.client

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import ktalex.dal.client.AuthorsClient
import ktalex.dal.query.QueryBuilder
import kotlin.coroutines.cancellation.CancellationException

class ClientTests : ShouldSpec({
    should("Not work if client is closed") {
        val client = AuthorsClient()
        val query = QueryBuilder().select("displayName")

        client.getRandom(query)

        // if we close the client then it should not work anymore
        client.close()

        shouldThrow<CancellationException> {
            client.getRandom(query)
        }
    }
})
