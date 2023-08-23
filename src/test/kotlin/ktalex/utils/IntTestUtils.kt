package ktalex.utils

import io.kotest.matchers.ints.shouldBeGreaterThan

fun Int.shouldNotBeNegative() = this.shouldBeGreaterThan(-1)