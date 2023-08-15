package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class DisplayNames(
    val displayName: Map<String, String>
)