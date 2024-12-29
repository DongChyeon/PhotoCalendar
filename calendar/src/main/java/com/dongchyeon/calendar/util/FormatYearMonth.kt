package com.dongchyeon.calendar.util

import com.dongchyeon.calendar.model.DateYearMonth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

internal fun formatYearMonthInEnglish(yearMonth: DateYearMonth): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.set(yearMonth.year, yearMonth.month - 1, 1)
    val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    return sdf.format(calendar.time)
}

internal fun formatYearMonthInKorean(yearMonth: DateYearMonth): String {
    return "${yearMonth.year}년 ${yearMonth.month}월"
}