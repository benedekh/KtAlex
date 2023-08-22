package ktalex.utils

import io.kotest.matchers.booleans.shouldBeTrue

fun <T : Comparable<T>> Iterable<T>.shouldBeSorted(isDescending: Boolean = false): Iterable<T> {
    var isSorted = true
    var previous: T? = null
    this.forEach {
        if (previous == null) {
            previous = it
        } else {
            val isAscendingAndSmallerThanPrevious = !isDescending && (previous!! > it)
            val isDescendingAndGreaterThanPrevious = isDescending && (previous!! < it)
            if (isAscendingAndSmallerThanPrevious || isDescendingAndGreaterThanPrevious) {
                isSorted = false
                return@forEach
            }
            previous = it
        }
    }
    isSorted.shouldBeTrue()
    return this
}
