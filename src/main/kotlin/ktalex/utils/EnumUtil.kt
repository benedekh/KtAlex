package ktalex.utils

import java.util.*

object EnumUtil {
    inline fun <reified T : Enum<T>> valueOfOrNull(name: String): T? {
        return try {
            enumValueOf<T>(name.uppercase(Locale.ENGLISH).replace(" ", "_"))
        } catch (ex: IllegalArgumentException) {
            null
        }
    }
}