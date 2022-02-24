package com.example.anew.util

import java.util.*

private const val SECOND = 1
private const val MINUTE = 60 * SECOND
private const val HOUR = 60 * MINUTE
private const val DAY = 24 * HOUR
private const val MONTH = 30 * DAY
private const val YEAR = 12 * MONTH

private fun currentDate(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

// Long: time in millisecond
fun Long.toTimeAgo(): String {
    val time = this
    val now = currentDate()

    // convert back to second
    val diff = (now - time) / 1000

    return when {
        diff < MINUTE -> "Just now"
        diff < 60 * MINUTE -> "${diff / MINUTE} min ago"
        diff < 24 * HOUR -> "${diff / HOUR} h ago"
        diff < 30 * DAY -> "${diff / DAY} d ago"
        diff < 12 * MONTH -> "${diff / MONTH} m ago"
        else -> "${diff / YEAR} y ago"
    }
}