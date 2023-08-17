package ktalex.dal.ngrams

import kotlinx.serialization.Serializable
import ktalex.model.Ngram

@Serializable
data class NgramMetaInfo(
    val count: Int?,
    val doi: String?,
    val openalexId: String?
)

@Serializable
data class NgramsResponse(
    val meta: NgramMetaInfo?,
    val ngrams: List<Ngram>?
)