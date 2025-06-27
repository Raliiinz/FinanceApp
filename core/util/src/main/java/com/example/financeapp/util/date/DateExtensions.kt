package com.example.financeapp.util.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

/**
 * Конвертирует Date для работы с календарем.
 */
fun Date?.toMillisAtNoon(): Long? = this?.let { date ->
    Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, 12)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun Long.toDateAtStartOfDay(): Date = Calendar.getInstance().apply {
    timeInMillis = this@toDateAtStartOfDay
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}.time

fun Date?.toStartOfDay(): Long? = this?.let { date ->
    Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun Date?.toEndOfDay(): Long? = this?.let { date ->
    Calendar.getInstance().apply {
        time = date
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}

fun Long.isBetween(min: Long?, max: Long?): Boolean {
    val minCheck = min?.let { this >= it } ?: true
    val maxCheck = max?.let { this <= it } ?: true
    return minCheck && maxCheck
}

fun LocalDate.formatDate(): String {
    return format(DateTimeFormatter.ISO_LOCAL_DATE)
}

fun LocalDate.getMonthRange(): Pair<String, String> {
    return Pair(
        withDayOfMonth(1).formatDate(),
        formatDate()
    )
}

