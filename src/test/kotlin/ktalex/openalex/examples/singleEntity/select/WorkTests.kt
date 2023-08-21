package ktalex.openalex.examples.singleEntity.select

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.should
import ktalex.dal.client.WorksClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-single-entities/select-fields)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorksClient

    beforeTest {
        client = WorksClient()
    }
    afterTest {
        client.close()
    }

    should("Select the ID and the displayName fields of a work") {
        val result = client.getByOpenAlexId("W2138270253", QueryBuilder().select("id", "displayName"))
        result.shouldNotBeNull()

        result.should {
            it.id.shouldNotBeNull()
            it.id!!.url.shouldBeSet()
            it.id!!.id.shouldBeSet()
            it.displayName.shouldBeSet()

            it.apcList.shouldBeNull()
            it.abstractInvertedIndex.shouldBeNull()
            it.apcPaid.shouldBeNull()
            it.authorships.shouldBeNull()
            it.abstractInvertedIndex.shouldBeNull()
            it.authorships.shouldBeNull()
            it.apcList.shouldBeNull()
            it.apcPaid.shouldBeNull()
            it.bestOaLocation.shouldBeNull()
            it.biblio.shouldBeNull()
            it.citedByApiUrl.shouldBeNull()
            it.citedByCount.shouldBeNull()
            it.concepts.shouldBeNull()
            it.correspondingAuthorIds.shouldBeNull()
            it.correspondingInstitutionIds.shouldBeNull()
            it.countsByYear.shouldBeNull()
            it.createdDate.shouldBeNull()
            it.doi.shouldBeNull()
            it.grants.shouldBeNull()
            it.institutionsDistinctCount.shouldBeNull()
            it.isAuthorsTruncated.shouldBeNull()
            it.isOa.shouldBeNull()
            it.isParatext.shouldBeNull()
            it.isRetracted.shouldBeNull()
            it.language.shouldBeNull()
            it.license.shouldBeNull()
            it.locations.shouldBeNull()
            it.locationsCount.shouldBeNull()
            it.mesh.shouldBeNull()
            it.ngramsUrl.shouldBeNull()
            it.openAccess.shouldBeNull()
            it.primaryLocation.shouldBeNull()
            it.publicationDate.shouldBeNull()
            it.publicationYear.shouldBeNull()
            it.referencedWorks.shouldBeNull()
            it.referencedWorksCount.shouldBeNull()
            it.relevanceScore.shouldBeNull()
            it.relatedWorks.shouldBeNull()
            it.sustainableDevelopmentGoals.shouldBeNull()
            it.title.shouldBeNull()
            it.type.shouldBeNull()
            it.typeCrossref.shouldBeNull()
            it.updatedDate.shouldBeNull()
        }
    }
})
