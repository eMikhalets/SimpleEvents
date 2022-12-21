package com.emikhalets.simpleevents.presentation.screens.event_item

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.dialogs.DeleteEventDialog
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.presentation.theme.backgroundSecondary
import com.emikhalets.simpleevents.presentation.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDateFull

@Composable
fun EventItemScreen(
    eventId: Long,
    viewModel: EventItemViewModel,
    onEventDeleted: () -> Unit,
    onEventEditClick: (Long) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessage = state.error!!.asString(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) onEventDeleted()
    }

    if (state.event != null) {
        EventItemScreen(
            event = state.event!!,
            onImageClick = {},
            onEditClick = { onEventEditClick(eventId) },
            onDeleteClick = { showDeleteDialog = true }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AppText(
                text = stringResource(R.string.error_internal),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        }
    }

    if (showDeleteDialog) {
        DeleteEventDialog(
            onCancelClick = {
                showDeleteDialog = false
            },
            onDeleteClick = {
                viewModel.deleteEvent(state.event)
                showDeleteDialog = false
            }
        )
    }

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun EventItemScreen(
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
            color = MaterialTheme.colors.backgroundSecondary,
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
private fun EventItemHeader(
    event: EventEntity,
    onImageClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        AppIcon(
            drawableRes = R.drawable.ic_round_person_24,
            modifier = Modifier
                .size(150.dp)
                .background(MaterialTheme.colors.backgroundSecondary)
                .clickable { onImageClick() }
                .padding(top = 32.dp)
        )
        AppText(
            text = event.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp)
        )
        AppText(
            text = event.date.formatDateFull(event.withoutYear),
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        if (!event.withoutYear) {
            AppText(
                text = stringResource(R.string.event_item_turns, event.age),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        AppText(
            text = event.days.toString(),
            color = MaterialTheme.colors.onBackgroundSecondary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(top = 16.dp)
                .background(
                    color = MaterialTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun EventItemContent(
    event: EventEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            AppText(
                text = stringResource(R.string.event_item_notes_title).uppercase(),
                color = MaterialTheme.colors.secondary,
                letterSpacing = 1.sp,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            AppText(
                text = event.note.ifEmpty { stringResource(R.string.event_item_notes_empty) },
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        AppButton(
            text = stringResource(R.string.event_item_btn_edit),
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp)
        )
        AppButton(
            text = stringResource(R.string.event_item_btn_delete),
            onClick = onDeleteClick,
            backgroundColor = MaterialTheme.colors.error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        EventItemScreen(
            event = EventEntity(
                name = "Test Full Name",
                date = System.currentTimeMillis(),
                eventType = EventType.BIRTHDAY,
                note = "Some note text",
                withoutYear = false
            ).apply {
                days = 6
                age = 42
            },
            onImageClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}
