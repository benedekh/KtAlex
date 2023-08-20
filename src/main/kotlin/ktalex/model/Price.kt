package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val price: Int?,
    val currency: String?,
)
