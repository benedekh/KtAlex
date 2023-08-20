package ktalex.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.camelToSnakeCase(): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "_$0").lowercase()
}

/**
 * Extracts the first match of a part from a string. After that it replaces all occurrences of that part with an empty string.
 *
 * @param partName the name of the part to be extracted
 * @param isDecimal whether the part is a decimal number or not
 */
fun String.extractFirstMatch(partName: String, isDecimal: Boolean): Pair<String?, String> {
    var remainingString: String = this
    var firstMatch: String? = null

    val regexps = if (isDecimal) {
        listOf(Regex("\\?$partName=(\\d+)(&|$)"), Regex("&$partName=(\\d+)(&|$)"))
    } else {
        listOf(Regex("\\?$partName=([^&]+)(&|$)"), Regex("&$partName=([^&]+)(&|$)"))
    }

    // find the regex that matches first
    var lowestMatchingIndex: Int = Int.MAX_VALUE
    var regexWithLowestMatchingIndex: Regex? = null
    regexps.forEach { regexp ->
        regexp.find(remainingString)?.let { matchResult ->
            val matchIndex = remainingString.indexOf(matchResult.value)
            if (matchIndex < lowestMatchingIndex) {
                lowestMatchingIndex = matchIndex
                regexWithLowestMatchingIndex = regexp
            }
        }
    }

    regexWithLowestMatchingIndex?.let { regexp ->
        firstMatch = regexp.find(remainingString)!!.let { it.groupValues[1] }

        // remove all matches from the string
        regexps.forEach {
            it.findAll(remainingString).forEach { matchResult ->
                val toBeCut = matchResult.value
                val lengthToBeCut = if (toBeCut.endsWith("&")) {
                    toBeCut.length - 1
                } else {
                    toBeCut.length
                }
                remainingString = remainingString.removeRange(
                    remainingString.indexOf(toBeCut),
                    remainingString.indexOf(toBeCut) + lengthToBeCut
                )
            }
        }
    }

    return Pair(firstMatch, remainingString)
}

fun String.urlEncode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())