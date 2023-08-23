package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.InstitutionClient
import ktalex.utils.shouldBeSet

class InstitutionTests : ShouldSpec({
    lateinit var client: InstitutionClient

    beforeTest {
        client = InstitutionClient()
    }
    afterTest {
        client.close()
    }

    should("Get I27837315") {
        val openAlexId = "I27837315"
        val institution = client.getByOpenAlexId(openAlexId)
        institution.shouldNotBeNull()
        institution.id.shouldNotBeNull()
        institution.id!!.id.shouldBe(openAlexId)
        institution.id!!.url.shouldBe("https://openalex.org/$openAlexId")

        institution.createdDate.shouldNotBeNull()
        institution.createdDate!!.date.shouldNotBeNull()

        institution.updatedDate.shouldNotBeNull()
        institution.updatedDate!!.date.shouldNotBeNull()

        institution.type.shouldNotBeNull()
        institution.type!!.enum.shouldBe(InstitutionType.EDUCATION)

        institution.international.shouldNotBeNull()
        institution.international!!.displayName.shouldNotBeNull()

        val expectedRelationshipTypes = listOf(RelationshipType.RELATED, RelationshipType.CHILD)
        val associatedInstitutions = institution.associatedInstitutions
        associatedInstitutions.shouldNotBeNull()
        associatedInstitutions.forEach {
            it.relationship.shouldNotBeNull()
            expectedRelationshipTypes.contains(it.relationship!!.enum).shouldBeTrue()
        }

        val expectedRoles = listOf(RoleEnum.FUNDER, RoleEnum.INSTITUTION, RoleEnum.PUBLISHER)
        val roles = institution.roles
        roles.shouldNotBeNull()
        roles.forEach {
            it.role.shouldNotBeNull()
            expectedRoles.contains(it.role!!.enum).shouldBeTrue()
        }

        val resolvedWorks = institution.resolveWorks()?.results
        resolvedWorks.shouldNotBeNull()
        resolvedWorks.size.shouldBe(25)
        resolvedWorks.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }
    }
})
