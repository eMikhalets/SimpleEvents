package com.emikhalets.simpleevents.ui.screens.event_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.ui.screens.common.DeletingEventDialog
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsButton
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsNegativeButton
import com.emikhalets.simpleevents.ui.screens.common.navToEditEvent
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDateFull
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

@Composable
fun EventItemScreen(
    eventId: Long,
    viewModel: EventItemViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect("") {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) navController.popBackStack()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    if (state.event != null) {
        EventItemScreen(
            event = state.event,
            onImageClick = { scaffoldState.showSnackBar(context, R.string.add_event_empty_name) },
            onEditClick = { navController.navToEditEvent(eventId) },
            onDeleteClick = { showDeleteDialog = true }
        )
    }

    if (showDeleteDialog) {
        DeletingEventDialog(
            onConfirmClick = {
                viewModel.deleteEvent(state.event)
                showDeleteDialog = false
            },
            onDismissClick = { showDeleteDialog = false }
        )
    }
}

@Composable
fun EventItemScreen(
    event: EventEntity,
    onImageClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        EventItemHeader(
            event = event,
            onImageClick = onImageClick
        )
        Divider(
            color = MaterialTheme.colors.background,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        EventItemContent(
            event = event,
            onEditClick = onEditClick,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
fun EventItemHeader(
    event: EventEntity,
    onImageClick: () -> Unit,
) {
    Spacer(modifier = Modifier.height(32.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "",
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colors.backgroundSecondary)
                .clickable { onImageClick() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = event.name,
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = event.date.formatDateFull(event.withoutYear),
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp
        )
        if (!event.withoutYear) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.event_item_turns, event.age),
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = event.days.toString(),
            color = MaterialTheme.colors.onBackgroundSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EventItemContent(
    event: EventEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(16.dp))
        EventItemNoteBox(note = event.note)
        Spacer(modifier = Modifier.height(32.dp))
        SimpleEventsButton(
            text = stringResource(R.string.event_item_btn_edit),
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        SimpleEventsNegativeButton(
            text = stringResource(R.string.event_item_btn_delete),
            onClick = onDeleteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun EventItemNoteBox(note: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.secondary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.event_item_notes_title).uppercase(),
            color = MaterialTheme.colors.secondary,
            letterSpacing = 1.sp,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = note.ifEmpty { stringResource(R.string.event_item_notes_empty) },
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEventItemScreen() {
    SimpleEventsTheme {
        EventItemScreen(
            event = EventEntity(
                days = 6,
                age = 42,
                name = "Test Full Name",
                date = System.currentTimeMillis(),
                eventType = EventType.BIRTHDAY,
                note = "Some note text",
                withoutYear = false
            ),
            onImageClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}