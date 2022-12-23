package com.emikhalets.simpleevents.presentation.screens.settings

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.presentation.components.AppButton
import com.emikhalets.simpleevents.presentation.components.AppIcon
import com.emikhalets.simpleevents.presentation.components.AppIconButton
import com.emikhalets.simpleevents.presentation.components.AppText
import com.emikhalets.simpleevents.presentation.components.TimePicker
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
    viewModel: SettingsViewModel,
) {
    val context = LocalContext.current
    val prefs = Prefs(context)
    val state by viewModel.state.collectAsState()

    var hourInit by remember { mutableStateOf(prefs.eventAlarmHour) }
    var minuteInit by remember { mutableStateOf(prefs.eventAlarmMinute) }
    var notificationsAll by remember { mutableStateOf(prefs.eventAlarmsEnabled) }
    var alarmsEnabled by remember { mutableStateOf(AppAlarmManager.isAlarmsRunning(context)) }
    var errorMessage by remember { mutableStateOf("") }

    val documentCreator = documentCreator { uri -> viewModel.exportEvents(uri) }
    val documentPicker = documentPicker { uri -> viewModel.importEvents(uri) }

    LaunchedEffect(Unit) {
        viewModel.loadAllNotificationsGlobal()
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            errorMessage = state.error!!.asString(context)
            viewModel.resetError()
        }
    }

    LaunchedEffect(state.imported) {
        if (state.imported) {
            toast(context, R.string.settings_backup_imported_success)
            viewModel.resetImported()
        }
    }

    LaunchedEffect(state.exported) {
        if (state.exported) {
            toast(context, R.string.settings_backup_exported_success)
            viewModel.resetExported()
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
            viewModel.updateNotificationGlobal(notification, enabled)
        },
        onSwitchAllNotification = { enabled ->
            AppAlarmManager.cancelAlarm(context)
            notificationsAll = enabled
            prefs.eventAlarmsEnabled = enabled
        },
        onRestartNotifications = {
            AppAlarmManager.setEventsAlarm(context)
            alarmsEnabled = AppAlarmManager.isAlarmsRunning(context)
            toast(context, R.string.settings_alarms_restarted)
        },
        onExportClick = { documentCreator.createFile() },
        onImportClick = { documentPicker.openFile() }
    )

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
    eventAlarms: List<EventAlarm>,
    alarmsEnabled: Boolean,
    onTimeChange: (Int, Int) -> Unit,
    onSwitchNotification: (EventAlarm, Boolean) -> Unit,
    onSwitchAllNotification: (Boolean) -> Unit,
    onRestartNotifications: () -> Unit,
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
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
    eventAlarms: List<EventAlarm>,
    onSwitchNotification: (EventAlarm, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        eventAlarms.forEach { notification ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = notification.nameEn,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 16.dp)
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
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    AppTheme {
        SettingsScreen(
            hour = 7,
            minute = 9,
            enabled = true,
            eventAlarms = listOf(
                EventAlarm("Event time", true, 12),
                EventAlarm("Event time", false, 56),
                EventAlarm("Event time", true, 0),
            ),
            alarmsEnabled = false,
            onTimeChange = { _, _ -> },
            onSwitchNotification = { _, _ -> },
            onSwitchAllNotification = {},
            onRestartNotifications = {},
            onExportClick = {},
            onImportClick = {}
        )
    }
}
