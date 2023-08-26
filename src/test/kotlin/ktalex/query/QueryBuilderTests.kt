package ktalex.query

import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import ktalex.dal.query.NumberFieldValue
import ktalex.dal.query.QueryBuilder
import java.time.LocalDate

class QueryBuilderTests : ShouldSpec({

    should("Throw error, because page cannot be 0 or negative") {
        shouldThrowWithMessage<IllegalArgumentException>("Page must be greater than 0") {
            QueryBuilder().pagination(page = 0)
        }
        shouldThrowWithMessage<IllegalArgumentException>("Page must be greater than 0") {
            QueryBuilder().pagination(page = -1)
        }
    }

    should("Throw error, because cursor and page cannot be set at the same time") {
        shouldThrowWithMessage<IllegalArgumentException>("Cursor cannot be used with page") {
            QueryBuilder().pagination(page = 1, cursor = "*")
        }
    }

    should("Throw error, because cursor cannot be used with sampling") {
        shouldThrowWithMessage<IllegalArgumentException>("Cursor cannot be used with sampling") {
            QueryBuilder().sampling(12).pagination(cursor = "*")
        }
        shouldThrowWithMessage<IllegalArgumentException>("Cursor cannot be used with sampling") {
            QueryBuilder().pagination(cursor = "*").sampling(12)
        }
    }

    should("Generate a fairly complex, but correct query string") {
        val query = QueryBuilder().pagination(page = 2, perPage = 11).sampling(sampleSize = 12, sampleSeed = 42)
            .search("title", "radiation")
            .gt("publicationDate", LocalDate.of(2020, 1, 1))
            .lt("publicationDate", LocalDate.of(2020, 12, 31))
            .or("language", false, "en", "de")
            .notEq("isRetracted", true)
            .eq("hasFulltext", true)
            .or(
                fieldPath = "institutionsDistinctCount",
                negate = true,
                NumberFieldValue(5),
                NumberFieldValue(6)
            ).select("displayName")
            .build()

        val expectedQuery = "?filter=title.search:radiation,from_publication_date:2020-01-01," +
            "to_publication_date:2020-12-31,language:en%7Cde,is_retracted:false,has_fulltext:true," +
            "institutions_distinct_count:%215%7C6&select=display_name&sample=12&seed=42&page=2&per_page=11"
        query.shouldBe(expectedQuery)
    }

    should("Set cursor to * (default pagination)") {
        val query = QueryBuilder().withDefaultPagination().build()
        query.shouldBe("?cursor=*")
    }

    should("Set cursor to * but keep page size (default pagination)") {
        val query = QueryBuilder().pagination(perPage = 42).withDefaultPagination().build()
        query.shouldBe("?per_page=42&cursor=*")
    }

    should("Set keep already set pagination (default pagination)") {
        val query = QueryBuilder().pagination(page = 11).withDefaultPagination().build()
        query.shouldBe("?page=11")
    }

    should("Set keep already set pagination with page and page size (default pagination)") {
        val query = QueryBuilder().pagination(page = 11, perPage = 42).withDefaultPagination().build()
        query.shouldBe("?page=11&per_page=42")
    }
})
