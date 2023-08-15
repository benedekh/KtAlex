package ktalex.model

import kotlinx.serialization.Serializable
import ktalex.utils.EnumUtil

@Serializable
data class Role(
    val role: String,
    val id: String,
    val worksCount: Int
) {
    val roleEnum: RoleEnum? = EnumUtil.valueOfOrNull<RoleEnum>(role)
}

enum class RoleEnum {
    PUBLISHER, FUNDER, INSTITUTION
}