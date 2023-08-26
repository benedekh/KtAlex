package ktalex.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.camelToSnakeCase(): String {
    val pattern = "(?<=.)[A-Z]".toRegex()
    return this.replace(pattern, "_$0").lowercase()
}

/**
 * Extracts the first match of a part from a string. After that it replaces all occurrences of that part with an empty
 * string.
 *
 * @param partName the name of the part to be extracted
 * @param isDecimal whether the part is a decimal number or not
 */
fun String.extractFirstMatch(partName: String, isDecimal: Boolean): Pair<String?, String> {
    val regexps = if (isDecimal) {
        listOf(Regex("\\?$partName=(\\d+)(&|$)"), Regex("&$partName=(\\d+)(&|$)"))
    } else {
        listOf(Regex("\\?$partName=([^&]+)(&|$)"), Regex("&$partName=([^&]+)(&|$)"))
    }

    // find the regex that matches first
    var firstMatch: String? = null
    var lowestMatchingIndex: Int = Int.MAX_VALUE
    regexps.forEach { regexp ->
        regexp.find(this)?.let { matchResult ->
            val matchIndex = this.indexOf(matchResult.value)
            if (matchIndex < lowestMatchingIndex) {
                firstMatch = matchResult.groupValues[1]
                lowestMatchingIndex = matchIndex
            }
        }
    }

    var remainingString: String = this
    firstMatch?.let { remainingString = this.removeAllMatchesOfRegexps(regexps) }

    return Pair(firstMatch, remainingString)
}

fun String.removeAllMatchesOfRegexps(regexps: List<Regex>): String {
    var remainingString: String = this
    regexps.forEach {
        var matchResult: MatchResult?
        while (it.find(remainingString).also { match -> matchResult = match } != null) {
            val toBeCut = matchResult?.value.orEmpty()
            val lengthToBeCut = if (toBeCut.endsWith("&")) {
                toBeCut.length - 1
            } else {
                toBeCut.length
            }
            remainingString = remainingString.removeRange(
                remainingString.indexOf(toBeCut),
                remainingString.indexOf(toBeCut) + lengthToBeCut,
            )
        }
    }
    return remainingString
}

fun String.urlEncode(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
