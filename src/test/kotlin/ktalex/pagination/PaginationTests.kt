package ktalex.pagination

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import ktalex.dal.client.AuthorClient
import ktalex.dal.query.QueryBuilder
import ktalex.utils.shouldBeSet

class PaginationTests : ShouldSpec({

    lateinit var baseQueryBuilder: QueryBuilder

    lateinit var client: AuthorClient

    beforeTest {
        baseQueryBuilder = QueryBuilder().search("\"Benedek Horváth\"")
        client = AuthorClient()
    }

    afterTest {
        client.close()
    }

    should("Go through the pages, using the default pagination") {
        val firstPage = client.getEntities(baseQueryBuilder)

        val expectedCount = 2
        val meta = firstPage.meta
        meta.shouldNotBeNull()
        meta.count.shouldBe(expectedCount)
        meta.nextCursor.shouldBeSet()

        val firstPageResults = firstPage.results
        firstPageResults.shouldNotBeNull()
        firstPageResults.size.shouldBe(expectedCount)
        firstPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        val secondPage = firstPage.nextPage()
        secondPage.shouldBeNull()
    }

    should("Go through the pages, using the cursor pagination with page size 1") {
        val firstPage = client.getEntities(baseQueryBuilder.pagination(cursor = "*", perPage = 1))

        val expectedCount = 2
        val firstPageMeta = firstPage.meta
        firstPageMeta.shouldNotBeNull()
        firstPageMeta.count.shouldBe(expectedCount)
        firstPageMeta.nextCursor.shouldBeSet()

        val firstPageResults = firstPage.results
        firstPageResults.shouldNotBeNull()
        firstPageResults.size.shouldBe(1)
        firstPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        val secondPage = firstPage.nextPage()
        secondPage.shouldNotBeNull()

        val secondPageMeta = secondPage.meta
        secondPageMeta.shouldNotBeNull()
        secondPageMeta.count.shouldBe(expectedCount)
        secondPageMeta.nextCursor.shouldBeSet()

        val secondPageResults = secondPage.results
        secondPageResults.shouldNotBeNull()
        secondPageResults.size.shouldBe(1)
        secondPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        val thirdPage = secondPage.nextPage()
        thirdPage.shouldBeNull()
    }

    should("Go through the pages using the page pagination with page size 1") {
        val firstPage = client.getEntities(baseQueryBuilder.pagination(page = 1, perPage = 1))

        val expectedCount = 2
        val firstPageMeta = firstPage.meta
        firstPageMeta.shouldNotBeNull()
        firstPageMeta.count.shouldBe(expectedCount)
        firstPageMeta.nextCursor.shouldBeNull()

        val firstPageResults = firstPage.results
        firstPageResults.shouldNotBeNull()
        firstPageResults.size.shouldBe(1)
        firstPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        val secondPage = firstPage.nextPage()
        secondPage.shouldNotBeNull()

        val secondPageMeta = secondPage.meta
        secondPageMeta.shouldNotBeNull()
        secondPageMeta.count.shouldBe(expectedCount)
        secondPageMeta.nextCursor.shouldBeNull()

        val secondPageResults = secondPage.results
        secondPageResults.shouldNotBeNull()
        secondPageResults.size.shouldBe(1)
        secondPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        val thirdPage = secondPage.nextPage()
        thirdPage.shouldBeNull()
    }

    should("Return empty list when reading a non-existing page") {
        val emptyPage = client.getEntities(baseQueryBuilder.pagination(page = 2))
        emptyPage.results.shouldBeEmpty()
    }

    should("Throw error, because cursor and page cannot be set at the same time") {
        shouldThrow<IllegalArgumentException> {
            QueryBuilder().pagination(page = 1, cursor = "*")
        }
    }

    should("Go through the pages, using the default pagination and an iterator") {
        val pageableResults = client.getEntities(baseQueryBuilder)

        var pagesVisited = 0
        pageableResults.forEach {
            val expectedCount = 2
            val meta = it.meta
            meta.shouldNotBeNull()
            meta.count.shouldBe(expectedCount)
            meta.nextCursor.shouldBeSet()

            val results = it.results
            results.shouldNotBeNull()
            results.size.shouldBe(expectedCount)
            results.forEach { author ->
                author.displayName.shouldBeSet()
                author.id.shouldNotBeNull()
                author.id!!.id.shouldBeSet()
            }

            pagesVisited++
        }

        pagesVisited.shouldBe(1)
    }

    should("Go through the pages, using the cursor pagination with page size 1 and an iterator") {
        val pageableResults = client.getEntities(baseQueryBuilder.pagination(cursor = "*", perPage = 1))

        var pagesVisited = 0
        pageableResults.forEach {
            val expectedCount = 2
            val meta = it.meta
            meta.shouldNotBeNull()
            meta.count.shouldBe(expectedCount)
            meta.nextCursor.shouldBeSet()

            val results = it.results
            results.shouldNotBeNull()
            results.size.shouldBe(1)
            results.forEach { author ->
                author.displayName.shouldBeSet()
                author.id.shouldNotBeNull()
                author.id!!.id.shouldBeSet()
            }

            pagesVisited++
        }

        pagesVisited.shouldBe(2)
    }

    should("Go through the pages using the page pagination with page size 1 and an iterator") {
        val pageableResults = client.getEntities(baseQueryBuilder.pagination(page = 1, perPage = 1))

        var pagesVisited = 0
        pageableResults.forEach {
            val expectedCount = 2
            val meta = it.meta
            meta.shouldNotBeNull()
            meta.count.shouldBe(expectedCount)
            meta.nextCursor.shouldBeNull()

            val results = it.results
            results.shouldNotBeNull()
            results.size.shouldBe(1)
            results.forEach { author ->
                author.displayName.shouldBeSet()
                author.id.shouldNotBeNull()
                author.id!!.id.shouldBeSet()
            }

            pagesVisited++
        }

        pagesVisited.shouldBe(2)
    }

    should("Go through the pages using the page pagination with page size 1 and an iterator by hand") {
        val pageableResults = client.getEntities(baseQueryBuilder.pagination(page = 1, perPage = 1))
        val iterator = pageableResults.iterator()

        iterator.hasNext().shouldBeTrue()
        val firstPage = iterator.next()

        val firstPageMeta = firstPage.meta
        firstPageMeta.shouldNotBeNull()
        val expectedCount = 2
        firstPageMeta.count.shouldBe(expectedCount)
        firstPageMeta.nextCursor.shouldBeNull()

        val firstPageResults = firstPage.results
        firstPageResults.shouldNotBeNull()
        firstPageResults.size.shouldBe(1)
        firstPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }

        iterator.hasNext().shouldBeTrue()
        val secondPage = iterator.next()
        secondPage.shouldNotBeNull()

        val secondPageMeta = secondPage.meta
        secondPageMeta.shouldNotBeNull()
        secondPageMeta.count.shouldBe(expectedCount)
        secondPageMeta.nextCursor.shouldBeNull()

        val secondPageResults = secondPage.results
        secondPageResults.shouldNotBeNull()
        secondPageResults.size.shouldBe(1)
        secondPageResults.forEach {
            it.displayName.shouldBeSet()
            it.id.shouldNotBeNull()
            it.id!!.id.shouldBeSet()
        }
        
        iterator.hasNext().shouldBeFalse()
        shouldThrow<NoSuchElementException> {
            iterator.next()
        }
    }
})