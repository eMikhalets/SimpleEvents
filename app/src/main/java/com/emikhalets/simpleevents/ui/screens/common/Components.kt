package com.emikhalets.simpleevents.ui.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
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
import com.emikhalets.simpleevents.ui.entity.EventEntity
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.pluralsResource
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventListItem(event: EventEntity) {
    val date = SimpleDateFormat("EE, MM/dd", Locale.getDefault()).format(event.date)
    val type = stringResource(event.eventType.nameRes)
    val turns = stringResource(R.string.event_list_item_turns, event.ageTurns)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp)
            .padding(8.dp)
    ) {
        RowContainer(background = MaterialTheme.colors.background) {
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
        RowContainer(background = MaterialTheme.colors.backgroundSecondary) {
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
                text = "$date • $type • $turns",
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun RowContainer(background: Color, content: @Composable () -> Unit) {
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
private fun PreviewEventListItem() {
    SimpleEventsTheme {
        EventListItem(
            event = EventEntity(
                daysCount = 6,
                ageTurns = 42,
                name = "Test Full Name",
                date = System.currentTimeMillis(),
                eventType = EventType.BIRTHDAY
            )
        )
    }
}