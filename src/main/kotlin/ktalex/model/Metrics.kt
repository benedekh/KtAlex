package ktalex.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface BaseCountsByYear {
    val citedByCount: Int?
    val year: Int?
}

@Serializable
data class CitedByCountYear(
    override val citedByCount: Int?,
    override val year: Int?,
) : BaseCountsByYear

@Serializable
data class CountsByYear(
    override val citedByCount: Int?,
    override val year: Int?,
    val worksCount: Int?,
) : BaseCountsByYear

@Serializable
data class CitationMetrics(
    @SerialName("2yr_mean_citedness")
    val twoYearMeanCitedness: Float?,
    val hIndex: Int?,
    val i10Index: Int?,
)
