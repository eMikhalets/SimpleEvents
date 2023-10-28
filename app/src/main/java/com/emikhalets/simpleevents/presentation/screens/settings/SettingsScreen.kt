package com.emikhalets.simpleevents.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppIconButton
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.TimePicker
import com.emikhalets.simpleevents.presentation.components.dialogs.EditNotificationDialog
import com.emikhalets.simpleevents.presentation.components.dialogs.ErrorDialog
import com.emikhalets.simpleevents.presentation.theme.AppColors
import com.emikhalets.simpleevents.presentation.theme.AppTheme
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.Prefs
import com.emikhalets.simpleevents.utils.createFile
import com.emikhalets.simpleevents.utils.documentCreator
import com.emikhalets.simpleevents.utils.documentPicker
import com.emikhalets.simpleevents.utils.extensions.formatTime
import com.emikhalets.simpleevents.utils.extensions.toast
import com.emikhalets.simpleevents.utils.openFile

@Composable
fun SettingsScreen(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val context = LocalContext.current
    val prefs = Prefs(context)

    var hourInit by remember { mutableStateOf(prefs.eventAlarmHour) }
    var minuteInit by remember { mutableStateOf(prefs.eventAlarmMinute) }
    var notificationsAll by remember { mutableStateOf(prefs.eventAlarmsEnabled) }
    var alarmsEnabled by remember { mutableStateOf(AppAlarmManager.isAlarmsRunning(context)) }
    var errorMessage by remember { mutableStateOf("") }
    var editNotification by remember { mutableStateOf<AlarmModel?>(null) }

    val documentCreator = documentCreator { uri -> onAction(SettingsAction.ExportClick(uri)) }
    val documentPicker = documentPicker { uri -> onAction(SettingsAction.ImportClick(uri)) }

    LaunchedEffect(Unit) {
        onAction(SettingsAction.GetAlarms)
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessage = state.error.asString()
            onAction(SettingsAction.ApplyError)
        }
    }

    LaunchedEffect(state.imported) {
        if (state.imported) {
            toast(context, R.string.settings_backup_imported_success)
            onAction(SettingsAction.ApplyImported)
        }
    }

    LaunchedEffect(state.exported) {
        if (state.exported) {
            toast(context, R.string.settings_backup_exported_success)
            onAction(SettingsAction.ApplyExported)
        }
    }

    SettingsScreen(
        hour = hourInit,
        minute = minuteInit,
        enabled = notificationsAll,
        eventAlarms = state.eventAlarms,
        alarmsEnabled = alarmsEnabled,
        onTimeChange = { hour, minute ->
            prefs.eventAlarmHour = hour
            hourInit = hour
            prefs.eventAlarmMinute = minute
            minuteInit = minute
        },
        onSwitchNotification = { notification, enabled ->
            onAction(SettingsAction.SwitchAlarm(notification, enabled))
        },
        onSwitchAllNotification = { enabled ->
            AppAlarmManager.cancelAlarm(context)
            notificationsAll = enabled
            prefs.eventAlarmsEnabled = enabled
        },
        onEditNotificationClick = {
            editNotification = it
        },
        onRestartNotifications = {
            AppAlarmManager.setEventsAlarm(context)
            alarmsEnabled = AppAlarmManager.isAlarmsRunning(context)
            toast(context, R.string.settings_alarms_restarted)
        },
        onAddNotificationClick = {
            editNotification = AlarmModel(0, "", false, 0)
        },
        onExportClick = { documentCreator.createFile() },
        onImportClick = { documentPicker.openFile() }
    )

    if (editNotification != null) {
        EditNotificationDialog(
            notification = editNotification!!,
            onDismiss = { editNotification = null },
            onSaveClick = {
                onAction(SettingsAction.UpdateAlarm(it))
                editNotification = null
            },
            onDeleteClick = {
                onAction(SettingsAction.DeleteAlarm(it))
                editNotification = null
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
private fun SettingsScreen(
    hour: Int,
    minute: Int,
    enabled: Boolean,
    eventAlarms: List<AlarmModel>,
    alarmsEnabled: Boolean,
    onTimeChange: (Int, Int) -> Unit,
    onSwitchNotification: (AlarmModel, Boolean) -> Unit,
    onSwitchAllNotification: (Boolean) -> Unit,
    onEditNotificationClick: (AlarmModel) -> Unit,
    onRestartNotifications: () -> Unit,
    onAddNotificationClick: () -> Unit,
    onExportClick: () -> Unit,
    onImportClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsSection(
            header = stringResource(R.string.settings_notifications),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            AlarmEnabledRow(
                alarmsEnabled = alarmsEnabled,
                onRestartNotifications = onRestartNotifications
            )
            TimePicker(
                hour = hour,
                minute = minute,
                text = stringResource(R.string.settings_notifications_time,
                    formatTime(hour, minute)),
                onTimeSelected = onTimeChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (eventAlarms.isNotEmpty()) {
                SettingsAllNotifications(
                    enabled = enabled,
                    onSwitchAllNotification = onSwitchAllNotification,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            SettingsNotifications(
                enabled = enabled,
                eventAlarms = eventAlarms,
                onSwitchNotification = onSwitchNotification,
                onEditNotificationClick = onEditNotificationClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            if (eventAlarms.isNotEmpty()) {
                AppButton(
                    text = stringResource(R.string.settings_edit_notification_add),
                    onClick = onAddNotificationClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )
            }
        }
        SettingsSection(
            header = stringResource(R.string.settings_backup),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, top = 8.dp)
            ) {
                AppButton(
                    text = stringResource(R.string.settings_backup_export),
                    onClick = onExportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                AppButton(
                    text = stringResource(R.string.settings_backup_import),
                    onClick = onImportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun SettingsSection(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        AppText(
            text = header,
            color = MaterialTheme.colors.onSurface,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.surface)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        )
        content()
    }
}

@Composable
private fun SettingsAllNotifications(
    enabled: Boolean,
    onSwitchAllNotification: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        AppText(
            text = stringResource(R.string.settings_notifications_enable),
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(end = 16.dp)
        )
        Switch(
            checked = enabled,
            onCheckedChange = { onSwitchAllNotification(it) }
        )
    }
}

@Composable
private fun SettingsNotifications(
    enabled: Boolean,
    eventAlarms: List<AlarmModel>,
    onSwitchNotification: (AlarmModel, Boolean) -> Unit,
    onEditNotificationClick: (AlarmModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        eventAlarms.forEach { notification ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEditNotificationClick(notification) }
            ) {
                AppText(
                    text = notification.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(3f)
                )
                AppText(
                    text = notification.days.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Switch(
                    checked = notification.enabled,
                    enabled = enabled,
                    onCheckedChange = { onSwitchNotification(notification, it) }
                )
            }
        }
    }
}

@Composable
private fun AlarmEnabledRow(
    alarmsEnabled: Boolean,
    onRestartNotifications: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (alarmsEnabled) {
            AppIcon(
                drawableRes = R.drawable.ic_round_check_circle_24,
                tint = AppColors.Green_600
            )
        } else {
            AppIcon(
                drawableRes = R.drawable.ic_round_error_24,
                tint = MaterialTheme.colors.error
            )
        }
        AppText(
            text = if (alarmsEnabled) {
                stringResource(R.string.settings_alarms_enabled)
            } else {
                stringResource(R.string.settings_alarms_disabled)
            },
            color = if (alarmsEnabled) {
                MaterialTheme.colors.onBackground
            } else {
                MaterialTheme.colors.error
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        )
        AppIconButton(
            drawableRes = R.drawable.ic_round_refresh_24,
            onClick = onRestartNotifications,
            iconColor = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    AppTheme {
        SettingsScreen(
            hour = 7,
            minute = 9,
            enabled = true,
            eventAlarms = listOf(
                AlarmModel(0, "Event time", true, 12),
                AlarmModel(0, "Event time", false, 56),
                AlarmModel(0, "Event time", true, 0),
                AlarmModel(0, "Event time Event time Event time", true, 780009),
            ),
            alarmsEnabled = false,
            onTimeChange = { _, _ -> },
            onSwitchNotification = { _, _ -> },
            onSwitchAllNotification = {},
            onEditNotificationClick = {},
            onRestartNotifications = {},
            onAddNotificationClick = {},
            onExportClick = {},
            onImportClick = {}
        )
    }
}
