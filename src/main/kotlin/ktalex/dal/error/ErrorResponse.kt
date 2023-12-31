package ktalex.dal.error

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String?,
    val message: String?,
)
