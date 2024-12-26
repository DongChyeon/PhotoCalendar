package com.dongchyeon.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.dongchyeon.calendar.theme.CalendarTheme
import com.dongchyeon.calendar.ui.component.DayBackgroundImage
import com.dongchyeon.calendar.util.noRippleClickable
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    events: List<CalendarEvent> = emptyList(),
    currentYearMonth: YearMonth = YearMonth.from(LocalDate.now()),
    selectedDate: LocalDate = LocalDate.now(),
    headerConfig: CalendarHeaderConfig = CalendarHeaderConfig.default(),
    weekHeaderConfig: CalendarWeekHeaderConfig = CalendarWeekHeaderConfig.default(),
    dayConfig: CalendarDayConfig = CalendarDayConfig.default(),
    indicatorConfig: CalendarIndicatorConfig = CalendarIndicatorConfig.default(),
    calendarLanguage: CalendarLanguage = CalendarLanguage.EN,
    onDayClicked: (LocalDate) -> Unit
) {
    val scrollState = rememberLazyListState()
    var selectedYearMonth by remember { mutableStateOf(currentYearMonth) }
    val dayGap: Dp = 4.dp
    val isTablet = LocalConfiguration.current.smallestScreenWidthDp >= 600
    val itemWidth = (LocalConfiguration.current.screenWidthDp.dp - (dayGap * 8) - 16.dp) / 7f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var currentDay = selectedYearMonth.atDay(1)
        val days = mutableListOf<LocalDate>()
        for (i: Int in 1..selectedYearMonth.lengthOfMonth()) {
            days.add(currentDay)
            currentDay = currentDay.plusDays(1)
        }

        HorizontalCalendarHeader(
            modifier = Modifier.padding(horizontal = 12.dp),
            config = headerConfig,
            calendarLanguage = calendarLanguage,
            yearMonth = selectedYearMonth,
            isTablet = isTablet,
            onPrevClick = { selectedYearMonth = selectedYearMonth.minusMonths(1) },
            onNextClick = { selectedYearMonth = selectedYearMonth.plusMonths(1) }
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(days) { today ->
                val matchedEvent = events.find {
                    it.date.year == today.year &&
                            it.date.monthValue == today.monthValue &&
                            it.date.dayOfMonth == today.dayOfMonth
                }

                Day(
                    itemWidth = itemWidth,
                    weekHeaderConfig = weekHeaderConfig,
                    dayConfig = dayConfig,
                    indicatorConfig = indicatorConfig,
                    calendarLanguage = calendarLanguage,
                    day = today,
                    event = matchedEvent,
                    isTablet = isTablet,
                    selectedDate = selectedDate,
                    onDayClicked = onDayClicked
                )
            }
        }

        LaunchedEffect(selectedDate) {
            val scrollPosition =
                if (selectedDate.dayOfMonth - 4 < 0) 0 else selectedDate.dayOfMonth - 4

            scrollState.animateScrollToItem(scrollPosition)
        }
    }
}

@Composable
private fun HorizontalCalendarHeader(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    config: CalendarHeaderConfig,
    calendarLanguage: CalendarLanguage,
    isTablet: Boolean,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val formattedMonth = when (calendarLanguage) {
        CalendarLanguage.EN -> {
            val locale = java.util.Locale.ENGLISH
            val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", locale)
            yearMonth.atDay(1).format(formatter) // e.g., "February 2024"
        }
        CalendarLanguage.KO -> {
            "${yearMonth.year}년 ${yearMonth.monthValue}월" // e.g., "2024년 12월"
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formattedMonth,
            style = if (isTablet) config.tabletTextStyle else config.textStyle,
            color = config.textColor
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.lib_ic_arrow_left),
                contentDescription = null,
                tint = config.textColor,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onPrevClick()
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.lib_ic_arrow_right),
                contentDescription = null,
                tint = config.textColor,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable {
                        onNextClick()
                    }
            )
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
    weekHeaderConfig: CalendarWeekHeaderConfig,
    dayConfig: CalendarDayConfig,
    indicatorConfig: CalendarIndicatorConfig,
    calendarLanguage: CalendarLanguage,
    isTablet: Boolean,
    onDayClicked: (LocalDate) -> Unit
) {
    val koreanWeekdays = listOf("월", "화", "수", "목", "금", "토", "일")
    val englishWeekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    val dayName = if (calendarLanguage == CalendarLanguage.KO) {
        koreanWeekdays[day.dayOfWeek.value - 1]
    } else {
        englishWeekDays[day.dayOfWeek.value - 1]
    }
    val selected = (day == selectedDate)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = dayName,
                style = if (isTablet) weekHeaderConfig.tabletTextStyle else weekHeaderConfig.textStyle,
                color = weekHeaderConfig.textColor
            )

            Spacer(modifier = Modifier.height(15.dp))

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

                if (selected) {
                    Box(
                        modifier = Modifier
                            .size(itemWidth)
                            .clip(
                                shape = indicatorConfig.shape
                            )
                            .background(
                                color = indicatorConfig.indicatorColor
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
                            color = dayConfig.selectedDayTextColor
                        )
                    }
                } else {
                    Text(
                        text = day.dayOfMonth.toString(),
                        style = if (isTablet) {
                            dayConfig.tabletTextStyle
                        } else {
                            dayConfig.textStyle
                        },
                        color = dayConfig.dayTextColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHorizontalCalendar() {
    CalendarTheme {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }

        val events = listOf(
            CalendarEvent(
                date = LocalDate.now().plusDays(1),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(2),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(3),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(4),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(5),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(6),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = RoundedCornerShape(8.dp)
            ),
            CalendarEvent(
                date = LocalDate.now().plusDays(7),
                imgUrl = "https://picsum.photos/200/300",
                imgShape = CircleShape
            ),
        )

        HorizontalCalendar(
            selectedDate = selectedDate,
            events = events,
            calendarLanguage = CalendarLanguage.EN,
            onDayClicked = {
                selectedDate = it
            },
        )
    }
}