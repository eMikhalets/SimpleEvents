package com.emikhalets.simpleevents.ui.screens.home

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
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsHeaderText
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsIcon
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsTextField
import com.emikhalets.simpleevents.ui.screens.common.navToEvent
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDateThisYear
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

// TODO: загружать события в порядке возрастания daysLeft, с разделителем по месяцам
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
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
        onEventClick = { eventId ->
            navController.navToEvent(eventId)
        }
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
        eventsList.forEach { entity ->
            EventListItem(
                event = entity,
                onEventClick = onEventClick
            )
        }
    }
}

@Composable
private fun EventListItem(
    event: EventEntity,
    onEventClick: (Long) -> Unit,
) {
    val date = event.date.formatDateThisYear("EE, MM/dd")
    val type = stringResource(event.eventType.nameRes)
    val turns = stringResource(R.string.event_list_item_turns, event.ageTurns)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onEventClick(event.id) }
    ) {
        SquareColumn(background = MaterialTheme.colors.background) {
            Text(
                text = event.daysCount.toString(),
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = pluralsResource(R.plurals.event_list_item_days, event.daysCount),
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
                text = if (event.ageTurns == 0) "$date • $type" else "$date • $type • $turns",
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
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
    SimpleEventsTheme {
        HomeScreen(
            eventsList = listOf(
                EventEntity(
                    id = 0,
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ),
                EventEntity(
                    id = 0,
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ),
                EventEntity(
                    id = 0,
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
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