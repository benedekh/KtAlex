package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Society(
    val organization: String?,
    val url: String?
)