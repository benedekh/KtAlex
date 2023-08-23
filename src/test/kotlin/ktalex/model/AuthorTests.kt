package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.AuthorClient
import ktalex.utils.shouldBeSet

class AuthorTests : ShouldSpec({
    lateinit var client: AuthorClient

    beforeTest {
        client = AuthorClient()
    }
    afterTest {
        client.close()
    }

    should("Get 0000-0002-9805-1580 and resolve its works") {
        val orcid = "0000-0002-9805-1580"
        val expectedOpenAlexId = "A5089409936"
        val author = client.getByOrcid(orcid)
        author.shouldNotBeNull()
        author.id.shouldNotBeNull()
        author.id!!.id.shouldBe(expectedOpenAlexId)
        author.id!!.url.shouldBe("https://openalex.org/$expectedOpenAlexId")

        author.createdDate.shouldNotBeNull()
        author.createdDate!!.date.shouldNotBeNull()

        author.updatedDate.shouldNotBeNull()
        author.updatedDate!!.date.shouldNotBeNull()

        val resolvedWorks = author.resolveWorks()?.results
        resolvedWorks.shouldNotBeNull()
        resolvedWorks.size.shouldBe(7)
        resolvedWorks.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }
    }
})