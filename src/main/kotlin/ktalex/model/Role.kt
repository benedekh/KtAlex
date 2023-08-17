package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.model.serialization.SerializedEnum

@Serializable
data class Role(
    val role: SerializedEnum<RoleEnum>?,
    val id: String?,
    val worksCount: Int?
)

enum class RoleEnum {
    PUBLISHER, FUNDER, INSTITUTION
}