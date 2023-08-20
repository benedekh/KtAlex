package ktalex.dal.query

import ktalex.utils.camelToSnakeCase
import java.net.URLEncoder
import java.time.LocalDate

class QueryBuilder {

    // pagination
    var paginationSettings: PaginationSettings? = null
        private set

    // select
    private var selectFields: List<String>? = null

    // sort
    private var sortByField: String? = null
    private var sortDescending: Boolean = false

    // search
    private var searchTerm: String? = null

    // filter
    private val filters: MutableMap<String, MutableList<String>> = mutableMapOf()

    // sampling
    private var sampleSize: Int? = null
    private var sampleSeed: Int? = null

    // group by
    private var groupBy: String? = null

    fun copy(): QueryBuilder {
        val copy = QueryBuilder()
        copy.paginationSettings =
            PaginationSettings(paginationSettings?.page, paginationSettings?.perPage, paginationSettings?.cursor)
        copy.selectFields = selectFields?.toMutableList()
        copy.sortByField = sortByField
        copy.sortDescending = sortDescending
        copy.searchTerm = searchTerm
        this.filters.forEach { (key, value) -> copy.filters[key] = value.toMutableList() }
        copy.sampleSize = sampleSize
        copy.sampleSeed = sampleSeed
        copy.groupBy = groupBy
        return copy
    }

    fun pagination(page: Int? = null, perPage: Int? = null, cursor: String? = null): QueryBuilder {
        if (page != null) {
            if (page < 1) throw IllegalArgumentException("Page must be greater than 0")
            if (cursor != null) throw IllegalArgumentException("Cursor cannot be used with page")
        }
        if (cursor != null && sampleSize != null) throw IllegalArgumentException("Cursor cannot be used with sampling")
        if (perPage != null && (perPage < 1 || perPage > 200)) throw IllegalArgumentException("PerPage must be between 1 and 200")

        this.paginationSettings = PaginationSettings(page, perPage, cursor)

        return this
    }

    fun withDefaultPagination(): QueryBuilder {
        return if (paginationSettings == null || (paginationSettings?.page == null && paginationSettings?.cursor == null)) {
            pagination(perPage = paginationSettings?.perPage, cursor = "*")
        } else {
            this
        }
    }

    /**
     * @param sampleSize number of items to sample randomly
     * @param sampleSeed seed to get the same sample every time. You must provide a seed value when paging beyond the first page of results. Without a seed value, you might get duplicate records in your results. (See https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/sample-entity-lists)
     */
    fun sampling(sampleSize: Int, sampleSeed: Int? = null): QueryBuilder {
        if (sampleSize > 10000) throw IllegalArgumentException("Sample size cannot be greater than 10000")

        this.sampleSize = sampleSize
        this.sampleSeed = sampleSeed

        return this
    }

    /**
     * @param fieldPath fully qualified path of the field you want to group by (e.g. author.id)
     */
    fun groupBy(fieldPath: String): QueryBuilder {
        groupBy = fieldPath.camelToSnakeCase()
        return this
    }

    /**
     * @param fields name of the top-level fields in camelCase that you want to get in the response (i.e. displayName, id)
     */
    fun select(vararg fields: String): QueryBuilder {
        selectFields = fields.map { it.camelToSnakeCase() }
        return this
    }

    /**
     * @param field name of the top-level field in camelCase that you want to sort by (i.e. displayName)
     * @pparam descending whether to sort in descending order
     */
    fun sort(field: String, descending: Boolean = false): QueryBuilder {
        sortByField = field.camelToSnakeCase()
        sortDescending = descending
        return this
    }

    /**
     * Excerpts from the [documentation](https://docs.openalex.org/how-to-use-the-api/get-lists-of-entities/search-entities):
     *
     * When you search Works, the API looks for matches in titles, abstracts, and fulltext.
     * When you search Concepts, we look in each concept's display_name and description fields.
     * When you search Sources, we look at the display_name, alternate_titles, and abbreviated_title fields.
     * Searching Authors or Institutions will look for matches within each entities' display_name field.
     *
     * For most text search we remove stop words and use stemming to improve results. So words like "the" and "an" are
     * transparently removed, and a search for "possums" will also return records using the word "possum." With the
     * except of raw affiliation strings, we do not search within words but rather try to match whole words. So a search
     * with "lun" will not match the word "lunar".
     *
     * If you search for a multiple-word phrase, the algorithm will treat each word separately, and rank results higher
     * when the words appear close together. If you want to return only results where the exact phrase is used, just
     * enclose your phrase within quotes.
     *
     * Including any of the words AND, OR, or NOT in any of your searches will enable boolean search.
     * Those words must be UPPERCASE.
     *
     * Examples:
     *
     * - Search for works that mention "elmo" and "sesame street," but not the words "cookie" or "monster":
     * "elmo" AND "sesame street" NOT (cookie OR monster)
     *
     * - Get works with the exact phrase "fierce creatures":
     * "fierce creatures"
     *
     * - Get works with the words "fierce" and "creatures":
     * fierce creatures
     *
     * @param searchTerm: the term(s) to search for
     */
    fun search(searchTerm: String): QueryBuilder {
        this.searchTerm = URLEncoder.encode(searchTerm, "utf-8")
        return this
    }

    /***
     * Searches the field for the given term.
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. displayName, title)
     * @param searchTerm: the term(s) to search for. For further explanation see [search]
     */
    fun search(fieldPath: String, searchTerm: String): QueryBuilder {
        val preparedFieldName = fieldPath.camelToSnakeCase()
        val key = "$preparedFieldName.search"

        val preparedSearchTerm = URLEncoder.encode(searchTerm, "utf-8")
        filters[key] = mutableListOf(preparedSearchTerm)
        return this
    }

    /**
     * Adds an AND EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun eq(fieldPath: String, value: String): QueryBuilder = put(fieldPath, value)

    /**
     * Adds an AND EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun eq(fieldPath: String, value: Number): QueryBuilder = put(fieldPath, value.toString())

    /**
     * Adds an AND EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun eq(fieldPath: String, value: LocalDate): QueryBuilder = put(fieldPath, value.toString())

    /**
     * Adds an AND EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. isOa)
     * @param value value to filter by
     */
    fun eq(fieldPath: String, value: Boolean): QueryBuilder = put(fieldPath, value.toString())

    /**
     * Adds an AND NOT EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun notEq(fieldPath: String, value: String): QueryBuilder = put(fieldPath, "!$value")

    /**
     * Adds an AND NOT EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun notEq(fieldPath: String, value: Number): QueryBuilder = put(fieldPath, "<$value|>$value")

    /**
     * Adds an AND NOT EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. createdDate)
     * @param value value to filter by
     */
    fun notEq(fieldPath: String, value: LocalDate): QueryBuilder = put(fieldPath, "<$value|>$value")

    /**
     * Adds an AND NOT EQUALS operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. isOa)
     * @param value value to filter by
     */
    fun notEq(fieldPath: String, value: Boolean): QueryBuilder = put(fieldPath, (!value).toString())

    /**
     * Adds an AND GREATER THAN operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun gt(fieldPath: String, value: Number): QueryBuilder = put(fieldPath, ">$value")

    /**
     * Adds an AND SMALLER THAN operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. id, institutions.countryCode)
     * @param value value to filter by
     */
    fun lt(fieldPath: String, value: Number): QueryBuilder = put(fieldPath, "<$value")

    /**
     * Adds an AND GREATER THAN operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. createdDate)
     * @param value value to filter by
     */
    fun gt(fieldPath: String, value: LocalDate): QueryBuilder {
        val preparedField = prependToLastFieldName(fieldPath, "from_")
        return eq(preparedField, value)
    }

    /**
     * Adds an AND SMALLER THAN operator to the filter as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. createdDate)
     * @param value value to filter by
     */
    fun lt(fieldPath: String, value: LocalDate): QueryBuilder {
        val preparedField = prependToLastFieldName(fieldPath, "to_")
        return eq(preparedField, value)
    }

    /**
     * Adds an AND (NOT) EQUALS operator to the filter with OR operator between the values, as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. author.id)
     * @param negate whether to negate the filter (i.e. NOT (value1 OR value2 OR ...))
     * @param values values to filter by
     */
    fun or(fieldPath: String, negate: Boolean = false, vararg values: String): QueryBuilder =
        put(fieldPath, negate, values)

    /**
     * Adds an AND (NOT) EQUALS operator to the filter with OR operator between the values, as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. author.id)
     * @param negate whether to negate the filter (i.e. NOT (value1 OR value2 OR ...))
     * @param values values to filter by
     */
    fun or(fieldPath: String, negate: Boolean = false, vararg values: LocalDate): QueryBuilder =
        put(fieldPath, negate, values)

    /**
     * Adds an AND (NOT) EQUALS operator to the filter with OR operator between the values, as follows:
     *
     * @param fieldPath fully qualified path of the field you want to filter by (e.g. worksCount)
     * @param negate whether to negate the filter (i.e. NOT (value1 OR value2 OR ...))
     * @param values values to filter by.
     *
     * @see NumberFieldValue
     */
    fun or(fieldPath: String, negate: Boolean = false, vararg values: NumberFieldValue): QueryBuilder {
        values.map {
            if (it.greaterThan) {
                ">${it.value}"
            } else if (it.lessThan) {
                "<${it.value}"
            } else {
                it.value.toString()
            }
        }.let { put(fieldPath, negate, it.toTypedArray()) }

        return this
    }

    fun build(): String {
        val sb = StringBuilder()

        searchTerm?.let { sb.append("search=$it&") }

        if (filters.isNotEmpty()) {
            sb.append("filter=")
            val filter =
                filters
                    .map { (key, values) -> values.joinToString(",", transform = { "$key:$it" }) }
                    .joinToString(",")
            sb.append("$filter&")
        }

        selectFields?.let { sb.append("select=${it.joinToString(",")}&") }
        sortByField?.let {
            sb.append(
                "sort=$it${
                    if (sortDescending) {
                        ":desc"
                    } else {
                        ""
                    }
                }&"
            )
        }
        groupBy?.let { sb.append("group_by=$it&") }
        sampleSize?.let { sb.append("sample=$it&") }
        sampleSeed?.let { sb.append("seed=$it&") }
        paginationSettings?.page?.let { sb.append("page=$it&") }
        paginationSettings?.perPage?.let { sb.append("per_page=$it&") }
        paginationSettings?.cursor?.let { sb.append("cursor=$it&") }

        return if (sb.isNotEmpty()) {
            return "?${sb.toString().removeSuffix("&")}"
        } else {
            ""
        }
    }


    private fun prependToLastFieldName(fieldPath: String, prefix: String): String {
        val parts: MutableList<String> = fieldPath.split("\\.").toMutableList()
        val last = parts.last().prependIndent(prefix)
        parts[parts.size - 1] = last
        return parts.joinToString(".")
    }

    private fun put(fieldPath: String, value: String): QueryBuilder {
        val key = fieldPath.camelToSnakeCase()
        if (filters.containsKey(key)) {
            filters[key]!!.add(value)
        } else {
            filters[key] = mutableListOf(value)
        }
        return this
    }

    private fun <E> put(fieldPath: String, negate: Boolean, values: Array<E>): QueryBuilder {
        if (values.size > 50) throw IllegalArgumentException("Cannot use more than 50 values in OR filter")

        val value = values.joinToString("|")
        if (negate) {
            put(fieldPath, "!$value")
        } else {
            put(fieldPath, value)
        }

        return this
    }

    override fun toString(): String {
        return "QueryBuilder(paginationSettings=$paginationSettings, selectFields=$selectFields, sortByField=$sortByField, sortDescending=$sortDescending, searchTerm=$searchTerm, filters=$filters, sampleSize=$sampleSize, sampleSeed=$sampleSeed, groupBy=$groupBy)"
    }

}

/**
 * @param value the value of the number field
 * @param greaterThan whether the value should be greater than the given value. greaterThan and lessThan cannot be used together.
 * @param lessThan whether the value should be less than the given value. greaterThan and lessThan cannot be used together.
 */
data class NumberFieldValue(
    val value: Number,
    val greaterThan: Boolean = false,
    val lessThan: Boolean = false
) {
    init {
        if (greaterThan && lessThan) throw IllegalArgumentException("Cannot use both greater than, less than operators")
    }
}

data class PaginationSettings(
    val page: Int?,
    val perPage: Int?,
    val cursor: String?
)