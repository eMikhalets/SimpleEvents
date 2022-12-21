package com.emikhalets.simpleevents.presentation.screens.edit_event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.AppTextField
import com.emikhalets.simpleevents.presentation.components.AppTextScreenHeader
import com.emikhalets.simpleevents.presentation.components.DatePicker
import com.emikhalets.simpleevents.presentation.components.EventTypeSpinner
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.toast

@Composable
fun EditEventScreen(
    eventId: Long,
    viewModel: EditEventViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }
    var note by remember { mutableStateOf("") }
    var withoutYear by remember { mutableStateOf(false) }
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

    LaunchedEffect(state.updated) {
        if (state.updated) {
            toast(context, R.string.edit_event_updated)
            viewModel.resetUpdated()
        }
    }

    LaunchedEffect(state.event) {
        val event = state.event
        if (name.isEmpty() && event != null) {
            type = event.eventType
            name = event.name
            date = event.date
            note = event.note
            withoutYear = state.event!!.withoutYear
        }
    }

    val event = state.event
    if (event != null) {
        EditEventScreen(
            type = type,
            name = name,
            date = date,
            note = note,
            withoutYear = withoutYear,
            onTypeChange = { newType -> type = newType },
            onNameChanged = { newName -> name = newName },
            onDateChange = { newDate -> date = newDate },
            onNoteChange = { newNote -> note = newNote },
            onWithoutYearCheck = { newWithoutYear -> withoutYear = newWithoutYear },
            onSaveClick = {
                when {
                    name.isEmpty() -> {
                        toast(context, R.string.edit_event_empty_name)
                    }
                    date == 0L -> {
                        toast(context, R.string.edit_event_empty_date)
                    }
                    else -> {
                        val newEvent = event.copy(
                            name = name,
                            date = date,
                            eventType = type,
                            note = note,
                            withoutYear = withoutYear
                        )
                        viewModel.updateEvent(newEvent)
                    }
                }
            }
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

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
private fun EditEventScreen(
    type: EventType,
    name: String,
    date: Long,
    note: String,
    withoutYear: Boolean,
    onTypeChange: (EventType) -> Unit,
    onNameChanged: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onNoteChange: (String) -> Unit,
    onWithoutYearCheck: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTextScreenHeader(
            text = stringResource(R.string.edit_event_header)
        )
        EventTypeSpinner(
            initItem = type,
            onTypeSelected = onTypeChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        AppTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = stringResource(R.string.edit_event_placeholder_name),
            capitalization = KeyboardCapitalization.Words,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        DatePicker(
            timestamp = date,
            withoutYear = withoutYear,
            onDateSelected = onDateChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        AppTextField(
            value = note,
            onValueChange = onNoteChange,
            placeholder = stringResource(R.string.edit_event_placeholder_note),
            capitalization = KeyboardCapitalization.Sentences,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        ) {
            Checkbox(
                checked = withoutYear,
                onCheckedChange = { onWithoutYearCheck(it) }
            )
            AppText(
                text = stringResource(R.string.add_event_without_year),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AppButton(
                text = stringResource(R.string.edit_event_btn_save),
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        EditEventScreen(
            type = EventType.BIRTHDAY,
            name = "Some Test Name",
            date = System.currentTimeMillis(),
            note = "Some note Some note Some note Some note Some note Some note Some note",
            withoutYear = true,
            onTypeChange = {},
            onNameChanged = {},
            onDateChange = {},
            onNoteChange = {},
            onWithoutYearCheck = {},
            onSaveClick = {}
        )
    }
}
