package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Biblio(
    val volume: String?,
    val issue: String?,
    val firstPage: String?,
    val lastPage: String?,
)
