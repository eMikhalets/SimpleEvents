package com.emikhalets.simpleevents.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.ui.entity.EventEntity
import com.emikhalets.simpleevents.ui.screens.common.EventListItem
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType

@Composable
fun HomeScreen(scaffoldState: ScaffoldState) {
    var searchQuery by remember { mutableStateOf("") }

    HomeScreen(
        eventsList = emptyList(),
        searchQuery = searchQuery,
        onSearchQueryChange = { newQuery ->
            searchQuery = newQuery
        }
    )
}

@Composable
fun HomeScreen(
    eventsList: List<EventEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.home_upcoming_events),
            color = MaterialTheme.colors.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = {
                Text(
                    text = stringResource(R.string.home_search_placeholder)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = ""
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = MaterialTheme.colors.primary,
                textColor = MaterialTheme.colors.primary,
                backgroundColor = MaterialTheme.colors.backgroundSecondary,
                placeholderColor = MaterialTheme.colors.onBackgroundSecondary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        eventsList.forEach { entity -> EventListItem(event = entity) }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    SimpleEventsTheme {
        HomeScreen(
            eventsList = listOf(
                EventEntity(
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
                    eventType = EventType.BIRTHDAY
                ),
                EventEntity(
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
                    eventType = EventType.BIRTHDAY
                ),
                EventEntity(
                    daysCount = 6,
                    ageTurns = 42,
                    name = "Test Full Name",
                    date = System.currentTimeMillis(),
                    eventType = EventType.BIRTHDAY
                )
            ),
            searchQuery = "",
            onSearchQueryChange = {}
        )
    }
}