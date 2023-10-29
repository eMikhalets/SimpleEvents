package com.emikhalets.simpleevents.presentation.screens.events_list

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.core.ui.extentions.ScreenPreview
import com.emikhalets.simpleevents.core.ui.theme.AppTheme
import com.emikhalets.simpleevents.domain.asString
import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListContract.Action
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListContract.State
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDate
import com.emikhalets.simpleevents.utils.extensions.formatHomeInfo
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import java.util.Date

@Composable
fun EventsListScreen(
    viewModel: EventsListViewModel,
    onEventClick: (Long) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(state.error) {
        val error = state.error
        if (error != null) {
            errorMessage = error.asString(context)
            viewModel.setAction(Action.DropError)
        }
    }

    ScreenContent(
        state = state,
        searchQuery = searchQuery,
        onSearchQueryChange = { newQuery ->
            searchQuery = newQuery
            viewModel.setAction(Action.SearchEvents(searchQuery))
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
private fun ScreenContent(
    state: State,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onEventClick: (Long) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBox(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
        )
        EventsListBox(
            eventsMap = state.eventsMap,
            onEventClick = onEventClick
        )
    }
}

@Composable
private fun SearchBox(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text(stringResource(R.string.home_search_placeholder)) },
        leadingIcon = { AppIcon(R.drawable.ic_round_search_24) },
        shape = RoundedCornerShape(26.dp),
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EventsListBox(
    eventsMap: Map<Long, List<EventModel>>,
    onEventClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (eventsMap.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            eventsMap.forEach { (date, events) ->
                stickyHeader {
                    EventsListHeader(
                        date = date,
                        onHeaderClick = {}
                    )
                }
                items(events) { event ->
                    EventListItem(
                        event = event,
                        onEventClick = onEventClick
                    )
                }
            }
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

@Composable
private fun EventsListHeader(
    date: Long,
    onHeaderClick: (Long) -> Unit,
) {
    AppText(
        text = date.formatDate("MMMM yyyy"),
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onHeaderClick(date) }
    )
}

@Composable
private fun EventListItem(
    event: EventModel,
    onEventClick: (Long) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onEventClick(event.id) }
    ) {
        SquareColumn(backgroundColor = MaterialTheme.colors.primary) {
            AppText(
                text = event.days.toString(),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            AppText(
                text = pluralsResource(R.plurals.home_event_days, event.days),
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                letterSpacing = 2.sp
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
                color = MaterialTheme.colors.onSecondary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SquareColumn(
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = { content() },
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxHeight()
            .aspectRatio(1f)
    )
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = State(
                eventsMap = mapOf(
                    Date().time to listOf(
                        EventModel(
                            1, Date().time, "Name 1", EventType.BIRTHDAY,
                            "", false, 1, 12
                        ),
                        EventModel(
                            2, Date().time, "Name 2", EventType.BIRTHDAY,
                            "", false, 2, 12
                        ),
                    ),
                    Date().time + 1 to listOf(
                        EventModel(
                            3, Date().time, "Name 4", EventType.BIRTHDAY,
                            "", false, 5, 12
                        ),
                        EventModel(
                            4, Date().time, "Name 5", EventType.BIRTHDAY,
                            "", false, 6, 12
                        ),
                    ),
                )
            ),
            searchQuery = "Some query",
            onSearchQueryChange = {},
            onEventClick = {}
        )
    }
}
