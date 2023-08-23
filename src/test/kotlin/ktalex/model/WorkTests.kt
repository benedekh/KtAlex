package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.WorkClient
import ktalex.utils.shouldBeSet

class WorkTests : ShouldSpec({
    lateinit var client: WorkClient

    beforeTest {
        client = WorkClient()
    }
    afterTest {
        client.close()
    }

    should("Get 10.1103/physrevb.37.785") {
        val doi = "10.1103/physrevb.37.785"
        val expectedOpenAlexId = "W2023271753"
        val work = client.getByDoi(doi)
        work.shouldNotBeNull()
        work.id.shouldNotBeNull()
        work.id!!.id.shouldBe(expectedOpenAlexId)
        work.id!!.url.shouldBe("https://openalex.org/$expectedOpenAlexId")

        work.createdDate.shouldNotBeNull()
        work.createdDate!!.date.shouldNotBeNull()

        work.updatedDate.shouldNotBeNull()
        work.updatedDate!!.date.shouldNotBeNull()

        work.publicationDate.shouldNotBeNull()
        work.publicationDate!!.date.shouldNotBeNull()

        val openAccess = work.openAccess
        openAccess.shouldNotBeNull()
        openAccess.isOa!!.shouldBeTrue()
        openAccess.oaStatus!!.enum.shouldBe(OaStatus.GREEN)

        val expectedAuthorshipPositions = listOf(AuthorPosition.FIRST, AuthorPosition.MIDDLE, AuthorPosition.LAST)
        val authorships = work.authorships
        authorships.shouldNotBeNull()
        authorships.forEach {
            it.authorPosition.shouldNotBeNull()
            expectedAuthorshipPositions.contains(it.authorPosition!!.enum).shouldBeTrue()
        }

        val correspondingAuthors = work.correspondingAuthorIds
        correspondingAuthors.shouldNotBeNull()
        correspondingAuthors.map { it.resolveEntity() }.forEach {
            it.shouldNotBeNull()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.displayName.shouldBeSet()
        }

        val correspondingInstitutions = work.correspondingInstitutionIds
        correspondingInstitutions.shouldNotBeNull()
        correspondingInstitutions.map { it.resolveEntity() }.forEach {
            it.shouldNotBeNull()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.displayName.shouldBeSet()
        }

        val relatedWorks = work.relatedWorks
        relatedWorks.shouldNotBeNull()
        relatedWorks.map { it.resolveEntity() }.forEach {
            it.shouldNotBeNull()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }

        val referencedWorks = work.referencedWorks
        referencedWorks.shouldNotBeNull()
        referencedWorks.map { it.resolveEntity() }.forEach {
            it.shouldNotBeNull()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }

        val resolvedCitedBys = work.resolveCitedBys()?.results
        resolvedCitedBys.shouldNotBeNull()
        resolvedCitedBys.size.shouldBe(25)
        resolvedCitedBys.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }

        val resolvedNgrams = work.resolveNgrams()?.ngrams
        resolvedNgrams.shouldNotBeNull()
        resolvedNgrams.size.shouldBe(1068)
        resolvedNgrams.forEach {
            it.ngram.shouldBeSet()
            it.ngramTokens!!.shouldBePositive()
            it.ngramCount!!.shouldBePositive()
            it.termFrequency!!.shouldNotBeNull()
        }
    }
})
