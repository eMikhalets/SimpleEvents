package com.emikhalets.simpleevents.presentation.screens.edit_event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsButton
import com.emikhalets.simpleevents.presentation.components.DatePicker
import com.emikhalets.simpleevents.presentation.components.EventTypeSpinner
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsHeaderText
import com.emikhalets.simpleevents.presentation.screens.common.SimpleEventsTextField
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

@Composable
fun EditEventScreen(
    eventId: Long,
    viewModel: EditEventViewModel,
    scaffoldState: ScaffoldState,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }
    var note by remember { mutableStateOf("") }
    var withoutYear by remember { mutableStateOf(false) }

    LaunchedEffect("") {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(state.event) {
        if (name.isEmpty() && state.event != null) {
            type = state.event.eventType
            name = state.event.name
            date = state.event.date
            note = state.event.note
            withoutYear = state.event.withoutYear
        }
    }

    LaunchedEffect(state.updated) {
        if (state.updated > 0) scaffoldState.showSnackBar(context, R.string.edit_event_updated)
        else if (state.updated == 0) scaffoldState.showSnackBar(context,
            R.string.edit_event_update_error)
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    if (state.event != null) {
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
                    name.isEmpty() -> scaffoldState.showSnackBar(context,
                        R.string.edit_event_empty_name)
                    date == 0L -> scaffoldState.showSnackBar(context,
                        R.string.edit_event_empty_date)
                    else -> viewModel.updateEvent(name, date, type, note, withoutYear)
                }
            }
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
        SimpleEventsHeaderText(
            text = stringResource(R.string.edit_event_header)
        )
        EventTypeSpinner(
            onTypeSelected = onTypeChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        SimpleEventsTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = { Text(text = stringResource(R.string.edit_event_placeholder_name)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
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
        SimpleEventsTextField(
            value = note,
            onValueChange = onNoteChange,
            placeholder = { Text(text = stringResource(R.string.edit_event_placeholder_note)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
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
            Text(
                text = stringResource(R.string.add_event_without_year),
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SimpleEventsButton(
                text = stringResource(R.string.edit_event_btn_save),
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEditEventScreen() {
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