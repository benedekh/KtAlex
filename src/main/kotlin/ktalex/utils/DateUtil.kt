package ktalex.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

object DateUtil {

    fun toDate(date: String): LocalDate? {
        return try {
            LocalDate.parse(date)
        } catch (ex: DateTimeParseException) {
            null
        }
    }

    fun toDateTime(dateTime: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(dateTime)
        } catch (ex: DateTimeParseException) {
            null
        }
    }
}