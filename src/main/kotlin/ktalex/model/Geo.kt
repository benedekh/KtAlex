package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Geo(
    val city: String?,
    val geonamesCityId: String?,
    val region: String?,
    val countryCode: String?,
    val country: String?,
    val latitude: Float?,
    val longitude: Float?,
)
