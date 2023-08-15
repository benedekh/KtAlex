package ktalex.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val isOa: Boolean,
    val landingPageUrl: String,
    val license: String?,
    val source: DehydratedSource,
    val pdfUrl: String?,
    val version: String?
)