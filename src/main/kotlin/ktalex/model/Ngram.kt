package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Ngram(
    val ngram: String?,
    val ngramCount: Int?,
    val ngramTokens: Int?,
    val termFrequency: Float?
)