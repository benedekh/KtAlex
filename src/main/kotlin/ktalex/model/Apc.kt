package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Apc(
    val value: Int?,
    val currency: String?,
    val provenance: String?,
    val valueUsd: Int?
)