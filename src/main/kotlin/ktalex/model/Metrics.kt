package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
abstract class BaseCountsByYear {
    abstract val citedByCount: Int
    abstract val year: Int
}

@Serializable
data class CitedByCountYear(
    override val citedByCount: Int,
    override val year: Int,
) : BaseCountsByYear()

@Serializable
data class CountsByYear(
    override val citedByCount: Int,
    override val year: Int,
    val worksCount: Int,
) : BaseCountsByYear()

@Serializable
data class CitationMetrics(
    val twoYearMeanCitedness: Float, // TODO field is called 2yr_mean_citedness in the API
    val hIndex: Int,
    val i10Index: String
)