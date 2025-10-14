package com.dongchyeon.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dongchyeon.calendar.CalendarEvent
import com.dongchyeon.calendar.ui.Calendar
import com.dongchyeon.calendar.util.now
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(Color.White)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var selectedDate by remember { mutableStateOf(LocalDate.now()) }

            val events = listOf(
                CalendarEvent(
                    date = LocalDate.now().plus(DatePeriod(days = 1)),
                    imgUrl = "https://picsum.photos/200/300",
                    imgShape = CircleShape
                ),
                CalendarEvent(
                    date = LocalDate.now().plus(DatePeriod(days = 2)),
                    imgUrl = "https://picsum.photos/200/300",
                    imgShape = RoundedCornerShape(8.dp)
                ),
            )

            Calendar(
                selectedDate = selectedDate,
                events = events,
            ) {
                selectedDate = it
            }
        }
    }
}