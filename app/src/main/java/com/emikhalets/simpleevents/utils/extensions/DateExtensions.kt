package com.emikhalets.simpleevents.utils.extensions

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

// End and Start of dates

fun Long.getStartOfMonth(): Long {
    return this.localDate.withDayOfMonth(1).milliseconds
}

fun Long.getEndOfMonth(): Long {
    val days = this.localDate.lengthOfMonth()
    return this.localDate.withDayOfMonth(days).milliseconds
}

fun Long.getStartOfYear(): Long {
    return this.localDate.withDayOfYear(1).milliseconds
}

fun Long.getEndOfYear(): Long {
    val days = this.localDate.lengthOfYear()
    return this.localDate.withDayOfYear(days).milliseconds
}

// Date formatting

fun formatTime(hour: Int, minute: Int): String {
    val minuteString = if (minute < 10) "0$minute" else "$minute"
    return "$hour:$minuteString"
}

fun Long.formatDateFull(withoutYear: Boolean): String {
    val pattern = if (withoutYear) "EEEE, d MMM" else "EEEE, d MMM, yyyy"
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(this)
}

fun Long.formatDateMonth(): String =
    SimpleDateFormat("MMMM", Locale.ENGLISH).format(this)

fun Long.formatDate(pattern: String = "dd MM yyyy"): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Long.formatDateThisYear(pattern: String): String {
    val date = this.localDate.withYear(LocalDate.now().year).milliseconds
    return SimpleDateFormat(pattern, Locale.ENGLISH).format(date)
}

// Converters

val LocalDate.milliseconds: Long
    get() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

val Long.localDate: LocalDate
    get() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

val Long.localDateTime: LocalDateTime
    get() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDateTime()

// Calculations

val Long.turns: Int
    get() {
        val turns = ChronoUnit.YEARS.between(this.localDate, LocalDate.now()).toInt() + 1
        return if (turns < 0) 0 else turns
    }

val Long.daysLeft: Int
    get() {
        var left = ChronoUnit.DAYS
            .between(LocalDate.now(), this.localDate.withYear(LocalDate.now().year)).toInt()
        if (left < 0) left += Year.now().length()
        return left
    }
