package ktalex.model

data class Location(
    val isOa: Boolean,
    val landingPageUrl: String,
    val license: String,
    val source: Source,
    val pdfUrl: String,
    val version: String
)