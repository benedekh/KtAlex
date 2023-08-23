package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.PublisherClient
import ktalex.utils.shouldBeSet

class PublisherTests : ShouldSpec({
    lateinit var client: PublisherClient

    beforeTest {
        client = PublisherClient()
    }
    afterTest {
        client.close()
    }

    should("Get P4310319965") {
        val openAlexId = "P4310319965"
        val publisher = client.getByOpenAlexId(openAlexId)
        publisher.shouldNotBeNull()
        publisher.id.shouldNotBeNull()
        publisher.id!!.id.shouldBe(openAlexId)
        publisher.id!!.url.shouldBe("https://openalex.org/$openAlexId")

        publisher.createdDate.shouldNotBeNull()
        publisher.createdDate!!.date.shouldNotBeNull()

        publisher.updatedDate.shouldNotBeNull()
        publisher.updatedDate!!.date.shouldNotBeNull()

        val expectedRoles = listOf(RoleEnum.INSTITUTION, RoleEnum.PUBLISHER)
        val roles = publisher.roles
        roles.shouldNotBeNull()
        roles.forEach {
            it.role.shouldNotBeNull()
            expectedRoles.contains(it.role!!.enum).shouldBeTrue()
        }

        val resolvedSources = publisher.resolveSources()?.results
        resolvedSources.shouldNotBeNull()
        resolvedSources.size.shouldBe(25)
        resolvedSources.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.displayName.shouldBeSet()
        }
    }
})
