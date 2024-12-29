package com.dongchyeon.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dongchyeon.calendar.ui.Calendar
import com.dongchyeon.calendar.ui.theme.DongChyeonCalendarTheme
import com.dongchyeon.calendar.util.now
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DongChyeonCalendarTheme {
                Scaffold(
                    containerColor = Color.White
                ) {
                    Column(modifier = Modifier.padding(it)) {
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
        }
    }
}