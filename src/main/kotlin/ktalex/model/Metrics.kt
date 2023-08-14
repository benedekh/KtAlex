package ktalex.model

abstract class BaseCountsByYear(
    open val citedByCount: Int,
    open val year: Int,
)

data class CitedByCountYear(
    override val citedByCount: Int,
    override val year: Int,
) : BaseCountsByYear(citedByCount, year)

data class CountsByYear(
    override val citedByCount: Int,
    override val year: Int,
    val worksCount: Int,
) : BaseCountsByYear(citedByCount, year)

data class CitationMetrics(
    val twoYearMeanCitedness: Float, // TODO field is called 2yr_mean_citedness in the API
    val hIndex: Int,
    val i10Index: String
)