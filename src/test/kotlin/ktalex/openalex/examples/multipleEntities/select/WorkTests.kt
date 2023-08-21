package ktalex.openalex.examples.multipleEntities.select

import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContain
import ktalex.dal.client.WorkClient
import ktalex.dal.error.OpenAlexException
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/select-fields)
 */
class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Select the ID, DOI and the displayName fields of a list of works") {
        val response = client.getEntities(QueryBuilder().select("id", "doi", "displayName"))
        response.shouldNotBeNull()

        val results = response.results
        results.shouldNotBeNull()
        results.forEach {
            it.shouldNotBeNull()

            it.id.shouldNotBeNull()
            it.id!!.url.shouldBeSet()
            it.id!!.id.shouldBeSet()
            it.doi.shouldBeSet()

            // there might be faulty data which do not have a displayName
            it.displayName?.let { displayName ->
                displayName.shouldBeSet()
            }

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

    should("Throw exception if tries to access non-top-level field") {
        try {
            client.getEntities(QueryBuilder().select("openAccess.isOa"))
            fail("Should not reach here")
        } catch (ex: OpenAlexException) {
            ex.message.shouldContain("open_access.is_oa is not a valid select field")
        }
    }

    should("Throw exception if we use select in autocomplete") {
        try {
            client.autocomplete("greenhou", QueryBuilder().select("id"))
            fail("Should not reach here")
        } catch (ex: OpenAlexException) {
            ex.message.shouldContain("select is not a valid parameter for the entity autocomplete endpoint")
        }
    }

    should("Throw exception if we use select with group by") {
        try {
            client.getEntities(QueryBuilder().select("id").groupBy("oaStatus"))
            fail("Should not reach here")
        } catch (ex: OpenAlexException) {
            ex.message.shouldContain("select does not work with group_by")
        }
    }
})
