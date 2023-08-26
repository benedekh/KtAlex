package ktalex.query

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import ktalex.utils.extractFirstMatch

class QueryParamExtractionTests : ShouldSpec({

    should("Extract page, perPage and cursor query params") {
        val url = "https://api.openalex.org/v1/publications?cursor=*&per_page=10&page=1"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)
        val (perPage, urlAfterPerPageRemoved) = urlAfterPageRemoved.extractFirstMatch("per_page", true)
        val (cursor, urlAfterCursorRemoved) = urlAfterPerPageRemoved.extractFirstMatch("cursor", false)

        page.shouldBe("1")
        urlAfterPageRemoved.shouldBe("https://api.openalex.org/v1/publications?cursor=*&per_page=10")

        perPage.shouldBe("10")
        urlAfterPerPageRemoved.shouldBe("https://api.openalex.org/v1/publications?cursor=*")

        cursor.shouldBe("*")
        urlAfterCursorRemoved.shouldBe("https://api.openalex.org/v1/publications")
    }

    should("Extract per page and query params") {
        val url = "https://api.openalex.org/v1/publications?cursor=*&per_page=10"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)
        val (perPage, urlAfterPerPageRemoved) = urlAfterPageRemoved.extractFirstMatch("per_page", true)
        val (cursor, urlAfterCursorRemoved) = urlAfterPerPageRemoved.extractFirstMatch("cursor", false)

        page.shouldBeNull()
        urlAfterPageRemoved.shouldBe("https://api.openalex.org/v1/publications?cursor=*&per_page=10")

        perPage.shouldBe("10")
        urlAfterPerPageRemoved.shouldBe("https://api.openalex.org/v1/publications?cursor=*")

        cursor.shouldBe("*")
        urlAfterCursorRemoved.shouldBe("https://api.openalex.org/v1/publications")
    }

    should("Extract page from a hypothetical query string 1") {
        val url = "?ashfasdkh=asdasd&page=58&page=63&per_page=10&cursor=*&page=1"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)

        page.shouldBe("58")
        urlAfterPageRemoved.shouldBe("?ashfasdkh=asdasd&per_page=10&cursor=*")
    }

    should("Extract page from a hypothetical query string 2") {
        val url = "?page=996asdasd&page=zhakshfd89&page=63sxsfgs&per_page=10&cursor=*&page=1"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)

        page.shouldBe("1")
        urlAfterPageRemoved.shouldBe("?page=996asdasd&page=zhakshfd89&page=63sxsfgs&per_page=10&cursor=*")
    }

    should("Extract page from a hypothetical query string 3") {
        val url = "?page=996asdasd&page=zhakshfd89&page=63sxsfgs&per_page=10&cursor=*&page=1"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", false)

        page.shouldBe("996asdasd")
        urlAfterPageRemoved.shouldBe("&per_page=10&cursor=*")
    }

    should("Extract page from a hypothetical query string 4") {
        val url = "?a=996asdasd&page=zhakshfd89&b=63sxsfgs&per_page=10&cursor=*&page=1"

        val (page, urlAfterPageRemoved) = url.extractFirstMatch("page", true)

        page.shouldBe("1")
        urlAfterPageRemoved.shouldBe("?a=996asdasd&page=zhakshfd89&b=63sxsfgs&per_page=10&cursor=*")
    }
})
