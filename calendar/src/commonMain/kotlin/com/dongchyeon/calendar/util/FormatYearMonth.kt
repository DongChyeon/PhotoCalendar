package com.dongchyeon.calendar.util

import com.dongchyeon.calendar.model.DateYearMonth
import kotlinx.datetime.LocalDate

internal fun formatYearMonthInEnglish(yearMonth: DateYearMonth): String {
    val date = LocalDate(yearMonth.year, yearMonth.month, 1)

    val monthName = date.month.name.lowercase()
        .replaceFirstChar { it.uppercase() }

    return "$monthName ${date.year}"
}

internal fun formatYearMonthInKorean(yearMonth: DateYearMonth): String {
    return "${yearMonth.year}년 ${yearMonth.month}월"
}