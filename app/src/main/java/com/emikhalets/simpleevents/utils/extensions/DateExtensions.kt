package com.emikhalets.simpleevents.utils.extensions

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

fun Long.formatDate(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun Long.formatDateThisYear(pattern: String): String {
    val date = this.localDate.withYear(LocalDate.now().year).milliseconds
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}

val LocalDate.milliseconds: Long
    get() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

val Long.localDate: LocalDate
    get() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

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
