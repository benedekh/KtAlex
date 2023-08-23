package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.FunderClient

class FunderTests : ShouldSpec({
    lateinit var client: FunderClient

    beforeTest {
        client = FunderClient()
    }
    afterTest {
        client.close()
    }

    should("Get F4320332161") {
        val openAlexId = "F4320332161"
        val funder = client.getByOpenAlexId(openAlexId)
        funder.shouldNotBeNull()
        funder.id.shouldNotBeNull()
        funder.id!!.id.shouldBe(openAlexId)
        funder.id!!.url.shouldBe("https://openalex.org/$openAlexId")

        funder.createdDate.shouldNotBeNull()
        funder.createdDate!!.date.shouldNotBeNull()

        funder.updatedDate.shouldNotBeNull()
        funder.updatedDate!!.date.shouldNotBeNull()

        val expectedRoles = listOf(RoleEnum.FUNDER, RoleEnum.INSTITUTION, RoleEnum.PUBLISHER)
        val roles = funder.roles
        roles.shouldNotBeNull()
        roles.forEach {
            it.role.shouldNotBeNull()
            expectedRoles.contains(it.role!!.enum).shouldBeTrue()
        }
    }
})
