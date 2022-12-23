package com.emikhalets.simpleevents.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.extensions.formatDateFull
import com.emikhalets.simpleevents.utils.extensions.localDate
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import java.time.LocalDate

@Composable
fun DatePicker(
    timestamp: Long,
    withoutYear: Boolean,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        onDateSelected(LocalDate.now()
            .withYear(year)
            .withMonth(month + 1)
            .withDayOfMonth(day)
            .milliseconds)
    }

    val date = if (timestamp == 0L) LocalDate.now() else timestamp.localDate
    val datePicker = DatePickerDialog(
        context, listener, date.year, date.monthValue - 1, date.dayOfMonth
    )

    Picker(
        text = if (timestamp != 0L) {
            timestamp.formatDateFull(withoutYear)
        } else {
            stringResource(R.string.add_event_choose_date)
        },
        isActive = timestamp != 0L,
        onClick = { datePicker.show() },
        modifier = modifier
    )
}

@Composable
fun TimePicker(
    hour: Int,
    minute: Int,
    text: String,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listener = TimePickerDialog.OnTimeSetListener { _, newHour, newMinute ->
        onTimeSelected(newHour, newMinute)
    }

    val timePicker = TimePickerDialog(
        LocalContext.current, listener, hour, minute, true
    )

    Picker(
        text = text,
        isActive = true,
        onClick = { timePicker.show() },
        modifier = modifier
    )
}

@Composable
private fun Picker(
    text: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppText(
        text = text,
        color = if (isActive) {
            MaterialTheme.colors.onPrimary
        } else {
            MaterialTheme.colors.onSecondary
        },
        modifier = modifier
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.Gray),
            ) { onClick() }
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        Picker(text = "Some text",
            isActive = true,
            onClick = {},
            modifier = Modifier.padding(32.dp))
    }
}
