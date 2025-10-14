package com.dongchyeon.calendar.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun rememberCalendarItemWidth(dayGap: Dp = 4.dp): Pair<Boolean, Dp> {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val screenWidthDp = with(density) { windowInfo.containerSize.width.toDp() }

    val isTablet = screenWidthDp >= 600.dp
    val itemWidth = (screenWidthDp - (dayGap * 8) - 16.dp) / 7f

    return isTablet to itemWidth
}