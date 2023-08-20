package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class CustomMaps(
    val description: Map<String, String>?,
    val displayName: Map<String, String>?,
)
