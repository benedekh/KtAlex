package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Grant(
    val funder: String?,
    val funderDisplayName: String?,
    val awardId: String?
)