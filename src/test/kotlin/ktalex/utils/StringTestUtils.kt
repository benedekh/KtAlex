package ktalex.utils

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldNotBeBlank
import io.kotest.matchers.string.shouldNotBeEmpty

fun String?.shouldBeSet(): String? {
    this.shouldNotBeNull()
    this.shouldNotBeBlank()
    this.shouldNotBeEmpty()
    return this
}
