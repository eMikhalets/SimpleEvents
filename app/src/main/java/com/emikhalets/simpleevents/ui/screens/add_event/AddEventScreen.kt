package com.emikhalets.simpleevents.ui.screens.add_event

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsTextField
import com.emikhalets.simpleevents.ui.screens.common.navToEventAfterAdding
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.showSnackBar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddEventScreen(
    viewModel: AddEventViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val context = LocalContext.current
    val state = viewModel.state

    var type by remember { mutableStateOf(EventType.BIRTHDAY) }
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(0L) }

    LaunchedEffect(state.savedId) {
        if (state.savedId > 0) navController.navToEventAfterAdding(state.savedId)
    }

    AddEventScreen(
        type = type,
        onTypeChange = { newType -> type = newType },
        name = name,
        onNameChanged = { newName -> name = newName },
        date = date,
        onDateChange = { newDate -> date = newDate },
        onSaveClick = {
            when {
                name.isEmpty() -> scaffoldState.showSnackBar(context, R.string.add_event_empty_name)
                date == 0L -> scaffoldState.showSnackBar(context, R.string.add_event_empty_date)
                else -> viewModel.saveNewEvent(name, date, type)
            }
        }
    )
}

@Composable
fun AddEventScreen(
    type: EventType,
    onTypeChange: (EventType) -> Unit,
    name: String,
    onNameChanged: (String) -> Unit,
    date: Long,
    onDateChange: (Long) -> Unit,
    onSaveClick: () -> Unit,
) {
    val datePicker = datePicker { timestamp -> onDateChange(timestamp) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.add_event_adding_new_event),
            color = MaterialTheme.colors.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        AddEventTypeDropMenu(
            type = type,
            onTypeSelected = onTypeChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        SimpleEventsTextField(
            value = name,
            onValueChange = onNameChanged,
            placeholder = { Text(text = stringResource(R.string.add_event_placeholder_name)) },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (date != 0L) {
                SimpleDateFormat("EEEE, dd MMM, yyyy", Locale.getDefault()).format(date)
            } else {
                stringResource(R.string.add_event_choose_date)
            },
            color = if (date != 0L) {
                MaterialTheme.colors.primary
            } else {
                MaterialTheme.colors.onBackgroundSecondary
            },
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .background(
                    color = MaterialTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .clickable { datePicker.show() }
                .padding(16.dp)
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = onSaveClick
            ) {
                Text(
                    text = stringResource(R.string.add_event_btn_save),
                    color = MaterialTheme.colors.onBackground,
                    letterSpacing = 2.sp,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun AddEventTypeDropMenu(
    type: EventType,
    onTypeSelected: (EventType) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val items = EventType.values()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .background(
                    color = MaterialTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(type.nameRes),
                color = MaterialTheme.colors.primary,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .fillMaxWidth()
                    .weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                tint = MaterialTheme.colors.primary,
                contentDescription = ""
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { type ->
                DropdownMenuItem(
                    onClick = {
                        expanded = false
                        onTypeSelected(EventType.BIRTHDAY)
                    }
                ) {
                    Text(
                        text = stringResource(type.nameRes),
                        color = MaterialTheme.colors.primary,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun datePicker(onDateSelected: (Long) -> Unit): DatePickerDialog {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val listener = DatePickerDialog.OnDateSetListener { _, newYear, newMonth, newDay ->
        calendar.apply {
            set(Calendar.YEAR, newYear)
            set(Calendar.MONTH, newMonth)
            set(Calendar.DAY_OF_MONTH, newDay)
        }
        onDateSelected(calendar.timeInMillis)
    }

    return DatePickerDialog(context, listener, year, month, day)
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddEventScreen() {
    SimpleEventsTheme {
        AddEventScreen(
            type = EventType.BIRTHDAY,
            onTypeChange = {},
            name = "",
            onNameChanged = {},
            date = System.currentTimeMillis(),
            onDateChange = {},
            onSaveClick = {}
        )
    }
}