package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.utils.EnumUtil

@Serializable
data class OpenAccess(
    val anyRepositoryHasFulltext: Boolean,
    val isOa: Boolean,
    val oaStatus: String,
    val oaUrl: String?
) {
    val oaStatusEnum: OaStatusEnum? = EnumUtil.valueOfOrNull<OaStatusEnum>(oaStatus)
}

enum class OaStatusEnum {
    GOLD, GREEN, HYBRID, BRONZE, CLOSED
}