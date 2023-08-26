package ktalex.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.SourceClient
import ktalex.utils.shouldBeSet

class SourceTests : ShouldSpec({
    lateinit var client: SourceClient

    beforeTest {
        client = SourceClient()
    }
    afterTest {
        client.close()
    }

    should("Get S137773608") {
        val openAlexId = "S137773608"
        val source = client.getByOpenAlexId(openAlexId)
        source.shouldNotBeNull()
        source.id.shouldNotBeNull()
        source.id!!.id.shouldBe(openAlexId)
        source.id!!.url.shouldBe("https://openalex.org/$openAlexId")

        source.createdDate.shouldNotBeNull()
        source.createdDate!!.date.shouldNotBeNull()

        source.updatedDate.shouldNotBeNull()
        source.updatedDate!!.date.shouldNotBeNull()

        source.type.shouldNotBeNull()
        source.type!!.enum.shouldBe(SourceType.JOURNAL)

        val resolvedWorks = source.resolveWorks()?.results
        resolvedWorks.shouldNotBeNull()
        resolvedWorks.size.shouldBe(25)
        resolvedWorks.forEach {
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
            it.title.shouldBeSet()
        }
    }
})
