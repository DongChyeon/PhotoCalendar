package com.dongchyeon.calendar.util

import android.annotation.SuppressLint
import com.dongchyeon.calendar.model.DateYearMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

fun LocalDate.Companion.now(): LocalDate {
    return Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
}

internal fun LocalDate.previousOrSame(target: DayOfWeek): LocalDate {
    val currentOrdinal = this.dayOfWeek.ordinal
    val targetOrdinal = target.ordinal

    val diff = (7 + currentOrdinal - targetOrdinal) % 7

    return this.minus(DatePeriod(days = diff))
}

internal fun DateYearMonth.plusMonths(months: Int): DateYearMonth {
    val totalMonths = this.year * 12 + (this.month - 1)
    val newTotal = totalMonths + months
    val newYear = newTotal / 12
    val newMonth = (newTotal % 12) + 1
    return DateYearMonth(newYear, newMonth)
}

internal fun DateYearMonth.minusMonths(months: Int): DateYearMonth {
    return this.plusMonths(-months)
}
@SuppressLint("NewApi")
internal fun LocalDate.weekOfMonth(startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY): Int {
    val offset = (7 + this.dayOfWeek.ordinal - startDayOfWeek.ordinal) % 7
    return ((this.dayOfMonth + offset - 1) / 7) + 1
}
@SuppressLint("NewApi")
internal fun DateYearMonth.getNumberWeeks(
    startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
): Int {
    val firstDay = this.atDay(1)
    val lastDay = this.atEndOfMonth()

    val firstWeekNumber = firstDay.weekOfMonth(startDayOfWeek)
    val lastWeekNumber = lastDay.weekOfMonth(startDayOfWeek)

    return lastWeekNumber - firstWeekNumber + 1
}
