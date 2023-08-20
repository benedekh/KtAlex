package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.OaStatusSerializer
import ktalex.model.serialization.SerializedEnum

@Serializable
data class OpenAccess(
    val anyRepositoryHasFulltext: Boolean?,
    val isOa: Boolean?,
    @Serializable(with = OaStatusSerializer::class)
    val oaStatus: SerializedEnum<OaStatus>?,
    val oaUrl: String?,
)

enum class OaStatus {
    GOLD, GREEN, HYBRID, BRONZE, CLOSED
}
