package com.emikhalets.simpleevents.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.AppTextField
import com.emikhalets.simpleevents.presentation.components.AppTextScreenHeader
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.presentation.theme.backgroundSecondary
import com.emikhalets.simpleevents.presentation.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatHomeInfo
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import java.time.LocalDate

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onEventClick: (Long) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadAllEvents()
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessage = state.error!!.asString(context)
            viewModel.resetError()
        }
    }

    HomeScreen(
        eventsList = state.searchedEvents,
        searchQuery = searchQuery,
        onSearchQueryChange = { newQuery ->
            searchQuery = newQuery
            viewModel.searchEvents(searchQuery)
        },
        onEventClick = onEventClick
    )

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun HomeScreen(
    eventsList: List<HomeEvent>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onEventClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTextScreenHeader(
            text = stringResource(R.string.home_upcoming_events)
        )
        AppTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = stringResource(R.string.home_search_placeholder),
            leadingIcon = R.drawable.ic_round_search_24,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        )
        if (eventsList.isNotEmpty()) {
            eventsList.forEach { homeEvent ->
                EventListItem(
                    homeEvent = homeEvent,
                    onEventClick = onEventClick
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                AppText(
                    text = stringResource(R.string.home_empty_events),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }
}

@Composable
private fun EventListItem(
    homeEvent: HomeEvent,
    onEventClick: (Long) -> Unit,
) {
    when (homeEvent) {
        is HomeMonthHeader -> {
            AppText(
                text = homeEvent.monthName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.backgroundSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
        is HomeEventEntity -> {
            val event = homeEvent.event
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { onEventClick(event.id) }
            ) {
                SquareColumn(background = MaterialTheme.colors.primary) {
                    AppText(
                        text = event.days.toString(),
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    AppText(
                        text = pluralsResource(R.plurals.event_list_item_days, event.days),
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 14.sp,
                        letterSpacing = 2.sp
                    )
                }
                SquareColumn(background = MaterialTheme.colors.backgroundSecondary) {
                    AppIcon(
                        drawableRes = R.drawable.ic_round_person_24,
                        tint = MaterialTheme.colors.onBackgroundSecondary
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp)
                ) {
                    AppText(
                        text = event.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    AppText(
                        text = event.formatHomeInfo(),
                        color = MaterialTheme.colors.secondary,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
        content = { content() },
        modifier = Modifier
            .background(background)
            .fillMaxHeight()
            .aspectRatio(1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        HomeScreen(
            eventsList = listOf(
                HomeMonthHeader("Month header"),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 6
                    age = 0
                }),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 6
                    age = 1
                }),
                HomeMonthHeader("Month header"),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 0
                    age = 42
                }),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 1
                    age = 42
                }),
                HomeMonthHeader("Month header"),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 6
                    age = 42
                }),
                HomeEventEntity(EventEntity(
                    name = "Test Full Name",
                    date = LocalDate.of(2021, 1, 12).milliseconds,
                    eventType = EventType.BIRTHDAY,
                    note = "Some note text"
                ).apply {
                    days = 6
                    age = 42
                })
            ),
            searchQuery = "Some query",
            onSearchQueryChange = {},
            onEventClick = {}
        )
    }
}
