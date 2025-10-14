package com.dongchyeon.calendar.theme

import androidx.compose.ui.graphics.Color

data class CalendarColor(
    val backgroundColor: Color,
    val contentColor: Color,
    val selectedDayBackgroundColor: Color,
    val selectedDayContentColor: Color,
) {
    companion object {
        fun default() = CalendarColor(
            backgroundColor = Color.White,
            contentColor = Color.Black,
            selectedDayBackgroundColor = Color.Blue.copy(alpha = 0.4f),
            selectedDayContentColor = Color.White
        )
    }
}