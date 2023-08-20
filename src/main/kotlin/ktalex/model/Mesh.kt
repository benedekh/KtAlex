package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Mesh(
    val descriptorUi: String?,
    val descriptorName: String?,
    val qualifierUi: String?,
    val qualifierName: String?,
    val isMajorTopic: Boolean?,
)
