package com.emikhalets.simpleevents.presentation.screens.add_event

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
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.showSnackBar
import com.emikhalets.simpleevents.utils.extensions.turns

@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    scaffoldState: ScaffoldState,
    onEventAdded: (Long) -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }
    var withoutYear by remember { mutableStateOf(false) }

    LaunchedEffect(state.savedId) {
        if (state.savedId > 0) onEventAdded(state.savedId)
    }

    AddEventScreen(
        name = name,
        date = date,
        withoutYear = withoutYear,
        onTypeChange = { newType -> type = newType },
        onNameChanged = { newName -> name = newName },
        onDateChange = { newDate -> date = newDate },
        onWithoutYearCheck = { newWithoutYear -> withoutYear = newWithoutYear },
        onSaveClick = {
            when {
                name.isEmpty() -> scaffoldState.showSnackBar(context,
                    R.string.add_event_empty_name)
                date == 0L -> scaffoldState.showSnackBar(context, R.string.add_event_empty_date)
                else -> viewModel.saveNewEvent(name, date, type, withoutYear)
            }
        }
    )
}

@Composable
fun AddEventScreen(
    name: String,
    date: Long,
    withoutYear: Boolean,
    onTypeChange: (EventType) -> Unit,
    onNameChanged: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onWithoutYearCheck: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
) {
    var turns by remember { mutableStateOf(0) }
    var left by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        SimpleEventsHeaderText(
            text = stringResource(R.string.add_event_adding_new_event)
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
            placeholder = { Text(text = stringResource(R.string.add_event_placeholder_name)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        DatePicker(
            timestamp = date,
            withoutYear = withoutYear,
            onDateSelected = {
                onDateChange(it)
                turns = it.turns
                left = it.daysLeft
            },
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
        if (!withoutYear) {
            Text(
                text = stringResource(R.string.add_event_turns, turns),
                fontSize = 18.sp,
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            )
        }
        Text(
            text = stringResource(R.string.add_event_days_left, left),
            fontSize = 18.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 32.dp, start = 32.dp, bottom = 16.dp)
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SimpleEventsButton(
                text = stringResource(R.string.add_event_btn_save),
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddEventScreen() {
    AppTheme {
        AddEventScreen(
            name = "Some Test Name",
            date = System.currentTimeMillis(),
            withoutYear = true,
            onTypeChange = {},
            onNameChanged = {},
            onDateChange = {},
            onWithoutYearCheck = {},
            onSaveClick = {}
        )
    }
}