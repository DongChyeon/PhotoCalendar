package com.dongchyeon.calendar.model

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

data class DateYearMonth(
    val year: Int,
    val month: Int
) {
    override fun toString(): String = "$year-${month.toString().padStart(2, '0')}"

    internal fun atDay(day: Int): LocalDate {
        return LocalDate(year, month, day)
    }

    internal fun atEndOfMonth(): LocalDate {
        val firstDay = LocalDate(year, month, 1)
        val firstDayNextMonth = firstDay.plus(DatePeriod(months = 1))
        return firstDayNextMonth.minus(DatePeriod(days = 1))
    }

    internal fun lengthOfMonth(): Int {
        val firstDayThisMonth = LocalDate(year, month, 1)
        val firstDayNextMonth = firstDayThisMonth.plus(DatePeriod(months = 1))
        val lastDayThisMonth = firstDayNextMonth.minus(DatePeriod(days = 1))
        return lastDayThisMonth.dayOfMonth
    }

    companion object {
        internal fun from(localDate: LocalDate): DateYearMonth {
            return DateYearMonth(
                year = localDate.year,
                month = localDate.monthNumber
            )
        }
    }
}