package ktalex.dal.client.request

import io.ktor.client.request.*

fun HttpRequestData.toExtendedString(): String {
    val sb = StringBuilder("HttpRequestData(")
    sb.append("method: $method, ")
    sb.append("url: $url, ")
    sb.append("headers: $headers, ")
    sb.append("body: $body, ")
    val attributes = attributes.allKeys.joinToString(", ") { "$it: ${attributes[it]}" }
    sb.append("attributes: ${attributes.ifEmpty { "EmptyAttributes" }})")
    return sb.toString()
}