package ktalex.model

import ktalex.utils.EnumUtil

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