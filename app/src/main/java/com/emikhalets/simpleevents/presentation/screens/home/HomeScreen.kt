package com.emikhalets.simpleevents.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsHeaderText
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsIcon
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsTextField
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.presentation.theme.backgroundSecondary
import com.emikhalets.simpleevents.presentation.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDateMonth
import com.emikhalets.simpleevents.utils.extensions.formatHomeInfo
import com.emikhalets.simpleevents.utils.extensions.localDate
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import com.emikhalets.simpleevents.utils.extensions.showSnackBar
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    scaffoldState: ScaffoldState,
    onEventClick: (Long) -> Unit,
) {
    val state = viewModel.state

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect("") {
        viewModel.loadAllEvents()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    HomeScreen(
        eventsList = state.events,
        searchQuery = searchQuery,
        onSearchQueryChange = { newQuery ->
            searchQuery = newQuery
        },
        onEventClick = onEventClick
    )
}

@Composable
private fun HomeScreen(
    eventsList: List<EventEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onEventClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        SimpleEventsHeaderText(
            text = stringResource(R.string.home_upcoming_events)
        )
        SimpleEventsTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text(text = stringResource(R.string.home_search_placeholder)) },
            leadingIcon = { SimpleEventsIcon(imageVector = Icons.Default.Search) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        var lastMonth by remember { mutableStateOf(-1) }
        eventsList.forEach { entity ->
            val monthChanged = lastMonth != entity.date.localDate.monthValue
            if (monthChanged) lastMonth = entity.date.localDate.monthValue
            EventListItem(
                event = entity,
                header = monthChanged,
                onEventClick = onEventClick
            )
        }
    }
}

@Composable
private fun EventListItem(
    event: EventEntity,
    header: Boolean,
    onEventClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (header) {
            Text(
                text = event.date.formatDateMonth(),
                color = MaterialTheme.colors.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.backgroundSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(74.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onEventClick(event.id) }
        ) {
            SquareColumn(background = MaterialTheme.colors.background) {
                Text(
                    text = event.days.toString(),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = pluralsResource(R.plurals.event_list_item_days, event.days),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 14.sp,
                    letterSpacing = 2.sp
                )
            }
            SquareColumn(background = MaterialTheme.colors.backgroundSecondary) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackgroundSecondary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = event.name,
                    color = MaterialTheme.colors.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = event.formatHomeInfo(),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun SquareColumn(
    background: Color,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(background)
            .fillMaxHeight()
            .aspectRatio(1f)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    AppTheme {
        HomeScreen(
            eventsList = listOf(
                EventEntity(
                    days = 6,
                    age = 42,
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ),
                EventEntity(
                    days = 6,
                    age = 42,
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 2, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text",
                    withoutYear = false
                ),
                EventEntity(
                    days = 6,
                    age = 0,
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 2, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                )
            ),
            searchQuery = "",
            onSearchQueryChange = {},
            onEventClick = {}
        )
    }
}