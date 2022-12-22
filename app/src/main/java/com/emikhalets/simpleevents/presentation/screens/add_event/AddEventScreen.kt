package com.emikhalets.simpleevents.presentation.screens.add_event

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
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.toast
import com.emikhalets.simpleevents.utils.extensions.turns

@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    onEventAdded: (Long) -> Unit,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }
    var turns by remember { mutableStateOf(0) }
    var daysLeft by remember { mutableStateOf(0) }
    var withoutYear by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(state.savedId) {
        if (state.savedId > 0) onEventAdded(state.savedId)
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessage = state.error!!.asString(context)
            viewModel.resetError()
        }
    }

    AddEventScreen(
        name = name,
        date = date,
        turns = turns,
        daysLeft = daysLeft,
        withoutYear = withoutYear,
        onTypeChange = { newType -> type = newType },
        onNameChanged = { newName -> name = newName },
        onDateChange = { newDate -> date = newDate },
        onTurnsChange = { newTurns -> turns = newTurns },
        onDaysLeftChange = { newDaysLeft -> daysLeft = newDaysLeft },
        onWithoutYearCheck = { newWithoutYear -> withoutYear = newWithoutYear },
        onSaveClick = {
            when {
                name.isEmpty() -> {
                    toast(context, R.string.add_event_empty_name)
                }
                date == 0L -> {
                    toast(context, R.string.add_event_empty_date)
                }
                else -> {
                    viewModel.saveNewEvent(name, date, type, withoutYear)
                }
            }
        }
    )

    if (errorMessage.isNotEmpty()) {
        ErrorDialog(
            message = errorMessage,
            onOkClick = { errorMessage = "" }
        )
    }
}

@Composable
fun AddEventScreen(
    name: String,
    date: Long,
    turns: Int,
    daysLeft: Int,
    withoutYear: Boolean,
    onTypeChange: (EventType) -> Unit,
    onNameChanged: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTurnsChange: (Int) -> Unit,
    onDaysLeftChange: (Int) -> Unit,
    onWithoutYearCheck: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTextScreenHeader(
            text = stringResource(R.string.add_event_adding_new_event)
        )
        EventTypeSpinner(
            onTypeSelected = onTypeChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        AppTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = stringResource(R.string.add_event_placeholder_name),
            capitalization = KeyboardCapitalization.Words,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp, bottom = 16.dp)
        )
        DatePicker(
            timestamp = date,
            withoutYear = withoutYear,
            onDateSelected = {
                onDateChange(it)
                onTurnsChange(it.turns)
                onDaysLeftChange(it.daysLeft)
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
            AppText(
                text = stringResource(R.string.add_event_without_year),
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        if (!withoutYear) {
            AppText(
                text = stringResource(R.string.add_event_turns, turns),
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp)
            )
        }
        AppText(
            text = stringResource(R.string.add_event_days_left, daysLeft),
            fontSize = 18.sp,
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
            AppButton(
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
            turns = 13,
            daysLeft = 24,
            withoutYear = true,
            onTypeChange = {},
            onNameChanged = {},
            onDateChange = {},
            onTurnsChange = {},
            onDaysLeftChange = {},
            onWithoutYearCheck = {},
            onSaveClick = {}
        )
    }
}
