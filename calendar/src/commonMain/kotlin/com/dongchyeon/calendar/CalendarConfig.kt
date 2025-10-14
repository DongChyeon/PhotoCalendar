package com.dongchyeon.calendar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.dongchyeon.calendar.theme.Typography

data class CalendarHeaderConfig(
    val textStyle: TextStyle,
    val tabletTextStyle: TextStyle,
    val textColor: Color
) {
    companion object {
        fun default() = CalendarHeaderConfig(
            textStyle = Typography.titleLarge,
            tabletTextStyle = Typography.headlineLarge,
            textColor = Color.Black
        )
    }
}

data class CalendarWeekHeaderConfig(
    val textStyle: TextStyle,
    val tabletTextStyle: TextStyle,
    val textColor: Color
) {
    companion object {
        fun default() = CalendarWeekHeaderConfig(
            textStyle = Typography.titleMedium,
            tabletTextStyle = Typography.headlineMedium,
            textColor = Color.Black
        )
    }
}

data class CalendarDayConfig(
    val textStyle: TextStyle,
    val tabletTextStyle: TextStyle,
    val dayTextColor: Color,
    val selectedDayTextColor: Color
) {
    companion object {
        fun default() = CalendarDayConfig(
            textStyle = Typography.bodyMedium,
            tabletTextStyle = Typography.titleMedium,
            dayTextColor = Color.Black,
            selectedDayTextColor = Color.White
        )
    }
}

data class CalendarIndicatorConfig(
    val indicatorColor: Color,
    val shape: Shape
) {
    companion object {
        fun default() = CalendarIndicatorConfig(
            indicatorColor = Color.Blue.copy(
                alpha = 0.4f
            ),
            shape = CircleShape
        )
    }
}