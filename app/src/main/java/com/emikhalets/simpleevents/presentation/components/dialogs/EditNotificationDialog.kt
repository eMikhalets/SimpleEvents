package com.emikhalets.simpleevents.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.AppTextField
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.extensions.toast

@Composable
fun EditNotificationDialog(
    notification: AlarmModel,
    onDismiss: () -> Unit,
    onSaveClick: (AlarmModel) -> Unit,
    onDeleteClick: (AlarmModel) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        DialogLayout(
            notification = notification,
            onSaveClick = onSaveClick,
            onDeleteClick = onDeleteClick,
            onCancelClick = onDismiss
        )
    }
}

@Composable
private fun DialogLayout(
    notification: AlarmModel,
    onSaveClick: (AlarmModel) -> Unit,
    onDeleteClick: (AlarmModel) -> Unit,
    onCancelClick: () -> Unit,
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(notification.name) }
    var days by remember {
        mutableStateOf(if (notification.name.isNotEmpty()) notification.days else null)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 40.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        AppText(
            text = stringResource(R.string.settings_edit_notification_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        AppTextField(
            value = name,
            onValueChange = { name = it },
            capitalization = KeyboardCapitalization.Sentences,
            placeholder = stringResource(R.string.settings_edit_notification_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        AppTextField(
            value = if (days == null) "" else days.toString(),
            onValueChange = { days = if (it.isEmpty()) null else it.toInt() },
            placeholder = stringResource(R.string.settings_edit_notification_days),
            type = KeyboardType.Number,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 16.dp)
        ) {
            if (notification.name.isEmpty()) {
                AppButton(
                    text = stringResource(R.string.settings_edit_notification_cancel),
                    onClick = { onCancelClick() },
                    backgroundColor = MaterialTheme.colors.error,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

            } else {
                AppButton(
                    text = stringResource(R.string.settings_edit_notification_delete),
                    onClick = { onDeleteClick(notification) },
                    backgroundColor = MaterialTheme.colors.error,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
            }
            AppButton(
                text = stringResource(R.string.settings_edit_notification_save),
                onClick = {
                    when {
                        name.isEmpty() -> {
                            toast(context, R.string.settings_edit_notification_name_null)
                        }
                        days == null -> {
                            toast(context, R.string.settings_edit_notification_days_null)
                        }
                        else -> {
                            onSaveClick(notification.copy(name = name, days = days!!))
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        EditNotificationDialog(
            notification = AlarmModel(0, "Notification name", true, 30),
            onDismiss = {},
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}
