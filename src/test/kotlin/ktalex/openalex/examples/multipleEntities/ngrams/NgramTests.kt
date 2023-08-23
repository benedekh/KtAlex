package ktalex.openalex.examples.multipleEntities.ngrams

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.ints.shouldBePositive
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.NgramClient
import ktalex.dal.ngrams.NgramsResponse
import ktalex.utils.shouldBeSet

/**
 * Based on the [OpenAlex documentation](https://docs.openalex.org/api-entities/works/get-n-grams)
 */
class NgramTests : ShouldSpec({
    lateinit var client: NgramClient

    beforeTest {
        client = NgramClient()
    }
    afterTest {
        client.close()
    }

    should("Get n-grams for W2023271753") {
        val response = client.getByOpenAlexId("W2023271753")
        testW2023271753(response)
    }

    should("Get n-grams for 10.1103/physrevb.37.785") {
        val response = client.getByDoi("10.1103/physrevb.37.785")
        testW2023271753(response)
    }
})

private fun testW2023271753(response: NgramsResponse) {
    val expectedCount = 1068
    val meta = response.meta
    meta.shouldNotBeNull()
    meta.count.shouldBe(expectedCount)
    meta.openalexId.shouldBe("https://openalex.org/W2023271753")

    val results = response.ngrams
    results.shouldNotBeNull()
    results.size.shouldBe(expectedCount)
    results.forEach {
        it.ngram.shouldBeSet()
        it.ngramTokens!!.shouldBePositive()
        it.ngramCount!!.shouldBePositive()
        it.termFrequency!!.shouldNotBeNull()
    }
}
