package com.emikhalets.simpleevents.presentation.screens.events_calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.extensions.localDate
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import kotlin.math.ceil

@Composable
fun EventsCalendarScreen(
    state: EventsCalendarState,
    onAction: (EventsCalendarAction) -> Unit,
    onMonthClick: (Month) -> Unit,
) {
    var year by remember { mutableStateOf(LocalDate.now().year) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onAction(EventsCalendarAction.GetEvents)
    }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            errorMessage = error.asString()
            onAction(EventsCalendarAction.AcceptError)
        }
    }

    ScreenContent(
        state = state,
        year = year,
        onYearChange = { increase ->
            year += if (increase) 1 else -1
            // TODO: load event for year
        },
        onMonthClick = onMonthClick
    )

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun ScreenContent(
    state: EventsCalendarState,
    year: Int,
    onYearChange: (Boolean) -> Unit,
    onMonthClick: (Month) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        YearSwitcher(
            year = year,
            onYearChange = onYearChange
        )
        Divider()
        CalendarBox(
            timestamp = LocalDate.now().milliseconds,
            eventsList = state.eventsList,
            onMonthClick = onMonthClick
        )
    }
}

@Composable
private fun YearSwitcher(
    year: Int,
    onYearChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIos,
            contentDescription = null,
            modifier = Modifier.clickable { onYearChange(false) }
        )
        Text(
            text = year.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        Icon(
            imageVector = Icons.Default.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.clickable { onYearChange(false) }
        )
    }
}

@Composable
private fun CalendarBox(
    eventsList: List<EventEntity>,
    timestamp: Long,
    onMonthClick: (Month) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth()
    ) {
        repeat(12) { month ->
            item {
                MonthBox(
                    month = timestamp.localDate.withMonth(month + 1).month,
                    year = timestamp.localDate.year,
                    isLeapYear = timestamp.localDate.isLeapYear,
                    eventsList = eventsList.filter { it.date.localDate.monthValue == month + 1 },
                    onMonthClick = onMonthClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun MonthBox(
    month: Month,
    year: Int,
    isLeapYear: Boolean,
    eventsList: List<EventEntity>,
    onMonthClick: (Month) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { onMonthClick(month) }
    ) {
        MonthHeader(
            month = month
        )
        MonthDays(
            month = month,
            year = year,
            isLeapYear = isLeapYear,
            eventsList = eventsList
        )
    }
}

@Composable
private fun MonthHeader(
    month: Month,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault()),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su").forEach { name ->
                Text(
                    text = name,
                    fontSize = 10.sp,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
private fun MonthDays(
    month: Month,
    year: Int,
    isLeapYear: Boolean,
    eventsList: List<EventEntity>,
    modifier: Modifier = Modifier,
) {
    val daysInMonth = month.length(isLeapYear)
    val firstDayOfMonth = YearMonth.of(year, month).atDay(1).dayOfWeek.value % 7
    val weeks = ceil((firstDayOfMonth + daysInMonth).toDouble() / 7).toInt()

    Column(modifier = modifier) {
        repeat(weeks) { week ->
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (dayOfWeek in 0 until 7) {
                    val dayOfMonth = dayOfWeek + week * 7 - firstDayOfMonth + 1
                    if (dayOfMonth <= 0 || dayOfMonth > daysInMonth) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        MonthDay(
                            day = dayOfMonth,
                            eventsList = eventsList.filter {
                                it.date.localDate.dayOfMonth == dayOfMonth
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MonthDay(
    day: Int,
    eventsList: List<EventEntity>,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(2.dp)
            .background(color = if (eventsList.isNotEmpty()) MaterialTheme.colors.primary else Color.Transparent)
    ) {
        Text(
            text = day.toString(),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = if (eventsList.isNotEmpty()) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = EventsCalendarState(),
            year = 2023,
            onYearChange = {},
            onMonthClick = {}
        )
    }
}
