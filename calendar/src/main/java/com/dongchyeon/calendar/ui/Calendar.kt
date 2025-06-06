package com.dongchyeon.calendar.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dongchyeon.calendar.CalendarDayConfig
import com.dongchyeon.calendar.CalendarEvent
import com.dongchyeon.calendar.CalendarHeaderConfig
import com.dongchyeon.calendar.CalendarIndicatorConfig
import com.dongchyeon.calendar.CalendarLanguage
import com.dongchyeon.calendar.CalendarWeekHeaderConfig
import com.dongchyeon.calendar.R
import com.dongchyeon.calendar.model.DateYearMonth
import com.dongchyeon.calendar.theme.CalendarTheme
import com.dongchyeon.calendar.ui.component.DayBackgroundImage
import com.dongchyeon.calendar.util.formatYearMonthInEnglish
import com.dongchyeon.calendar.util.formatYearMonthInKorean
import com.dongchyeon.calendar.util.getNumberWeeks
import com.dongchyeon.calendar.util.minusMonths
import com.dongchyeon.calendar.util.noRippleClickable
import com.dongchyeon.calendar.util.now
import com.dongchyeon.calendar.util.plusMonths
import com.dongchyeon.calendar.util.previousOrSame
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent> = emptyList(),
    currentYearMonth: DateYearMonth = DateYearMonth.from(LocalDate.now()),
    selectedDate: LocalDate,
    headerConfig: CalendarHeaderConfig = CalendarHeaderConfig.default(),
    weekHeaderConfig: CalendarWeekHeaderConfig = CalendarWeekHeaderConfig.default(),
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
    indicatorConfig: CalendarIndicatorConfig = CalendarIndicatorConfig.default(),
    calendarLanguage: CalendarLanguage = CalendarLanguage.EN,
    onDayClicked: (LocalDate) -> Unit
) {
    var selectedMonth by remember { mutableStateOf(currentYearMonth) }
    val dayGap: Dp = 8.dp
    val isTablet = LocalConfiguration.current.smallestScreenWidthDp >= 600
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - (dayGap * 8) - 16.dp) / 7f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MonthSelector(
            yearMonth = selectedMonth,
            config = headerConfig,
            calendarLanguage = calendarLanguage,
            isTablet = isTablet,
            onPrevClick = { selectedMonth = selectedMonth.minusMonths(1) },
            onNextClick = { selectedMonth = selectedMonth.plusMonths(1) }
        )

        Spacer(modifier = Modifier.height(25.dp))

        WeekHeader(
            itemWidth = itemWidth,
            config = weekHeaderConfig,
            calendarLanguage = calendarLanguage,
            isTablet = isTablet
        )

        for (num: Long in 0L until selectedMonth.getNumberWeeks()) {
            Week(
                itemWidth = itemWidth,
                weekNumber = num,
                dayConfig = dayConfig,
                indicatorConfig = indicatorConfig,
                isTablet = isTablet,
                events = events,
                currentMonth = selectedMonth,
                selectedDate = selectedDate,
                onDayClicked = onDayClicked
            )
            if (num != selectedMonth.getNumberWeeks() - 1L) Spacer(modifier = Modifier.height(dayGap))
        }
    }
}

@Composable
private fun MonthSelector(
    modifier: Modifier = Modifier,
    config: CalendarHeaderConfig,
    calendarLanguage: CalendarLanguage,
    yearMonth: DateYearMonth,
    isTablet: Boolean,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val formattedMonth = when (calendarLanguage) {
        CalendarLanguage.EN -> {
            formatYearMonthInEnglish(yearMonth)
        }
        CalendarLanguage.KO -> {
            formatYearMonthInKorean(yearMonth)
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier.noRippleClickable {
                onPrevClick()
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.lib_ic_arrow_left
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }

        Text(
            text = formattedMonth,
            style = if (isTablet) config.tabletTextStyle else config.textStyle,
            color = config.textColor
        )

        Box(
            modifier = Modifier.noRippleClickable {
                onNextClick()
            },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.lib_ic_arrow_right
                ),
                tint = Color.Unspecified,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun WeekHeader(
    modifier: Modifier = Modifier,
    itemWidth: Dp,
    config: CalendarWeekHeaderConfig,
    calendarLanguage: CalendarLanguage,
    isTablet: Boolean
) {
    val weekdays = if (calendarLanguage == CalendarLanguage.KO) {
        listOf("일", "월", "화", "수", "목", "금", "토")
    } else {
        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    }

    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (day in weekdays) {
            Box(
                modifier = Modifier
                    .size(itemWidth),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    style = if (isTablet) config.tabletTextStyle else config.textStyle,
                    color = config.textColor
                )
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
private fun Week(
    modifier: Modifier = Modifier,
    itemWidth: Dp,
    events: List<CalendarEvent>,
    weekNumber: Long,
    currentMonth: DateYearMonth,
    selectedDate: LocalDate,
    dayConfig: CalendarDayConfig,
    indicatorConfig: CalendarIndicatorConfig,
    isTablet: Boolean,
    onDayClicked: (LocalDate) -> Unit
) {
    val beginningWeek = currentMonth.atDay(1).plus(DatePeriod(days = (weekNumber * 7).toInt()))
    var currentDay = beginningWeek.previousOrSame(DayOfWeek.SUNDAY)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0..6) {
            if (currentDay.monthNumber == currentMonth.month) {
                val matchedEvent = events.find {
                    it.date.year == currentDay.year &&
                    it.date.month == currentDay.month &&
                    it.date.dayOfMonth == currentDay.dayOfMonth
                }

                Day(
                    itemWidth = itemWidth,
                    dayConfig = dayConfig,
                    indicatorConfig = indicatorConfig,
                    isTablet = isTablet,
                    day = currentDay,
                    event = matchedEvent,
                    selectedDate = selectedDate,
                    onDayClicked = onDayClicked
                )
            } else {
                Box(modifier = Modifier.size(itemWidth))
            }
            currentDay = currentDay.plus(DatePeriod(days = 1))
        }
    }
}

@Composable
private fun Day(
    modifier: Modifier = Modifier,
    itemWidth: Dp,
    event: CalendarEvent? = null,
    day: LocalDate,
    selectedDate: LocalDate,
    dayConfig: CalendarDayConfig,
    indicatorConfig: CalendarIndicatorConfig,
    isTablet: Boolean,
    onDayClicked: (LocalDate) -> Unit
) {
    val selected = (day == selectedDate)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(itemWidth)
                    .noRippleClickable {
                        onDayClicked(day)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (event != null) {
                    DayBackgroundImage(
                        imageUrl = event.imgUrl,
                        imageShape = event.imgShape
                    )
                }

                Box(
                    modifier = Modifier
                        .size(itemWidth)
                        .background(
                            shape = indicatorConfig.shape,
                            color = if (selected) indicatorConfig.indicatorColor else Color.Transparent
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.dayOfMonth.toString(),
                        style = if (isTablet) {
                            dayConfig.tabletTextStyle
                        } else {
                            dayConfig.textStyle
                        },
                        color = if (selected) dayConfig.selectedDayTextColor else dayConfig.dayTextColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCalendar() {
    CalendarTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

        val events = listOf(
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plus(DatePeriod(days = 1)),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
        )

        Calendar(
            selectedDate = selectedDate,
            events = events,
            calendarLanguage = CalendarLanguage.EN,
            headerConfig = CalendarHeaderConfig.default().copy(
                textColor = Color.Red
            ),
            dayConfig = CalendarDayConfig.default().copy(
                dayTextColor = Color.Blue,
                selectedDayTextColor = Color.White

            ),
            indicatorConfig = CalendarIndicatorConfig(
                indicatorColor = Color.Yellow.copy(alpha = 0.4f),
                shape = RectangleShape
            ),
            onDayClicked = { selectedDate = it }
        )
    }
}