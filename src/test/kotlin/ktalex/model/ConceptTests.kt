package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.ConceptClient
import ktalex.utils.shouldBeSet

class ConceptTests : ShouldSpec({
    lateinit var client: ConceptClient

    beforeTest {
        client = ConceptClient()
    }
    afterTest {
        client.close()
    }

    should("Get C198291218 and resolve its works") {
        val openAlexId = "C198291218"
        val concept = client.getByOpenAlexId(openAlexId)
        concept.shouldNotBeNull()
        concept.id.shouldNotBeNull()
        concept.id!!.id.shouldBe(openAlexId)
        concept.id!!.url.shouldBe("https://openalex.org/$openAlexId")

        concept.createdDate.shouldNotBeNull()
        concept.createdDate!!.date.shouldNotBeNull()

        concept.updatedDate.shouldNotBeNull()
        concept.updatedDate!!.date.shouldNotBeNull()

        val resolvedWorks = concept.resolveWorks()?.results
        resolvedWorks.shouldNotBeNull()
        resolvedWorks.size.shouldBe(25)
        resolvedWorks.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }
    }
})
