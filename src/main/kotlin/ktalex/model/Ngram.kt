package ktalex.model

data class Ngram(
    val ngram: String,
    val ngramCount: Int,
    val ngramTokens: Int,
    val termFrequency: Float
)