package com.emikhalets.simpleevents.ui.screens.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.ui.theme.backgroundNegative
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.ui.theme.onBackgroundSecondary
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.formatDateFull
import com.emikhalets.simpleevents.utils.extensions.localDate
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import java.time.LocalDate

@Composable
fun SimpleEventsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        colors = TextFieldDefaults.textFieldColors(
            leadingIconColor = MaterialTheme.colors.primary,
            textColor = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.backgroundSecondary,
            placeholderColor = MaterialTheme.colors.onBackgroundSecondary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Composable
fun SimpleEventsHeaderText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun SimpleEventsEventTypeSpinner(
    onTypeSelected: (EventType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val items = EventType.values()
    onTypeSelected(items.first())

    var expanded by remember { mutableStateOf(false) }
    var itemTextRes by remember { mutableStateOf(items.first().nameRes) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.backgroundSecondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = !expanded }
                .clip(RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(itemTextRes),
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
                contentDescription = "",
                modifier = Modifier.rotate(if (!expanded) 0f else 180f)
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
                        itemTextRes = type.nameRes
                        onTypeSelected(type)
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
fun SimpleEventsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
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

@Composable
fun SimpleEventsNegativeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.backgroundNegative
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
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

@Composable
fun SimpleEventsDatePicker(
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

    Text(
        text = if (timestamp != 0L) {
            timestamp.formatDateFull(withoutYear)
        } else {
            stringResource(R.string.add_event_choose_date)
        },
        color = if (timestamp != 0L) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.onBackgroundSecondary
        },
        fontSize = 16.sp,
        modifier = modifier
            .background(
                color = MaterialTheme.colors.backgroundSecondary,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { datePicker.show() }
            .padding(16.dp)
    )
}

@Composable
fun SimpleEventsTimePicker(
    hourInit: Int,
    minuteInit: Int,
    title: String,
    onTimeSelected: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        onTimeSelected(hourOfDay, minute)
    }

    val timePicker = TimePickerDialog(
        LocalContext.current, listener, hourInit, minuteInit, true
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = Color.Black),
        ) { timePicker.show() }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        )
        Text(
            text = if (minuteInit < 10) {
                "$hourInit:0$minuteInit"
            } else {
                "$hourInit:$minuteInit"
            },
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun SimpleEventsIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
) {
    Icon(
        imageVector = imageVector,
        contentDescription = "",
        tint = tint,
        modifier = modifier
    )
}