package com.dongchyeon.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dongchyeon.calendar.ui.Calendar
import com.dongchyeon.calendar.ui.theme.DongChyeonCalendarTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DongChyeonCalendarTheme {
                var selectedDate by remember { mutableStateOf(LocalDate.now()) }

                Column {
                    Calendar(
                        selectedDate = selectedDate
                    ) {
                        selectedDate = it
                    }
                }
            }
        }
    }
}