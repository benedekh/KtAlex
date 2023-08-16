package ktalex.utils

import ktalex.model.AuthorPosition
import java.util.*

object EnumUtil {

    fun guessEnum(literal: String): Enum<*>? {
        return valueOfOrNull<AuthorPosition>(literal) ?: run {
            valueOfOrNull<ktalex.model.RoleEnum>(literal) ?: run {
                valueOfOrNull<ktalex.model.InstitutionType>(literal) ?: run {
                    valueOfOrNull<ktalex.model.RelationshipType>(literal) ?: run {
                        valueOfOrNull<ktalex.model.OaStatus>(literal) ?: run {
                            valueOfOrNull<ktalex.model.SourceType>(literal)
                        }
                    }
                }
            }
        }
    }

    private inline fun <reified T : Enum<T>> valueOfOrNull(literal: String?): T? {
        return try {
            if (literal == null) {
                null
            } else {
                enumValueOf<T>(literal.uppercase(Locale.ENGLISH).replace(" ", "_"))
            }
        } catch (ex: IllegalArgumentException) {
            null
        }
    }
}