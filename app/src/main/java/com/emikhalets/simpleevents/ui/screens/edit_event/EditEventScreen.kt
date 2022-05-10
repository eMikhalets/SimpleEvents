package com.emikhalets.simpleevents.ui.screens.edit_event

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsButton
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsDatePicker
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsEventTypeSpinner
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsHeaderText
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsTextField
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

@Composable
fun EditEventScreen(
    eventId: Long,
    viewModel: EditEventViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }
    var note by remember { mutableStateOf("") }

    LaunchedEffect("") {
        viewModel.loadEvent(eventId)
    }

    LaunchedEffect(state.event) {
        if (name.isEmpty() && state.event != null) {
            type = state.event.eventType
            name = state.event.name
            date = state.event.date
            note = state.event.note
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
            onTypeChange = { newType -> type = newType },
            onNameChanged = { newName -> name = newName },
            onDateChange = { newDate -> date = newDate },
            onNoteChange = { newNote -> note = newNote },
            onSaveClick = {
                when {
                    name.isEmpty() -> scaffoldState.showSnackBar(context,
                        R.string.edit_event_empty_name)
                    date == 0L -> scaffoldState.showSnackBar(context,
                        R.string.edit_event_empty_date)
                    else -> viewModel.updateEvent(name, date, type, note)
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
    onTypeChange: (EventType) -> Unit,
    onNameChanged: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onNoteChange: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SimpleEventsHeaderText(
            text = stringResource(R.string.edit_event_header)
        )
        SimpleEventsEventTypeSpinner(
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
        SimpleEventsDatePicker(
            timestamp = date,
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
    SimpleEventsTheme {
        EditEventScreen(
            type = EventType.BIRTHDAY,
            name = "Some Test Name",
            date = System.currentTimeMillis(),
            note = "Some note Some note Some note Some note Some note Some note Some note",
            onTypeChange = {},
            onNameChanged = {},
            onDateChange = {},
            onNoteChange = {},
            onSaveClick = {}
        )
    }
}