package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class SustainableDevelopmentGoal(
    val id: String?,
    val displayName: String?,
    val score: Float?
)