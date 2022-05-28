package com.emikhalets.simpleevents.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Switch
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsButton
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsTimePicker
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.Prefs
import com.emikhalets.simpleevents.utils.createFile
import com.emikhalets.simpleevents.utils.documentCreator
import com.emikhalets.simpleevents.utils.documentPicker
import com.emikhalets.simpleevents.utils.extensions.getDefaultNotificationsGlobal
import com.emikhalets.simpleevents.utils.extensions.showSnackBar
import com.emikhalets.simpleevents.utils.openFile

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val state = viewModel.state
    val context = LocalContext.current
    val prefs = Prefs(context)

    var hourInit by remember { mutableStateOf(prefs.getEventsHour()) }
    var minuteInit by remember { mutableStateOf(prefs.getEventsMinute()) }
    var notificationsAll by remember { mutableStateOf(prefs.getNotificationsEnabled()) }
    var importOld by remember { mutableStateOf(false) }
    var alarmsEnabled by remember { mutableStateOf(AppAlarmManager.isAlarmsRunning(context)) }

    val documentCreator = documentCreator { uri ->
    }

    val documentPicker = documentPicker { uri ->
        viewModel.importEvents(uri, importOld)
    }

    LaunchedEffect("") {
        viewModel.loadAllNotificationsGlobal()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    LaunchedEffect(state.imported) {
        if (state.imported) scaffoldState
            .showSnackBar(context, R.string.settings_backup_imported_success)
    }

    SettingsScreen(
        hour = hourInit,
        minute = minuteInit,
        enabled = notificationsAll,
        notificationsGlobal = state.notificationsGlobal,
        alarmsEnabled = alarmsEnabled,
        onTimeChange = { hour, minute ->
            prefs.setEventsHour(hour)
            hourInit = hour
            prefs.setEventsMinute(minute)
            minuteInit = minute
        },
        onSwitchNotification = { notification, enabled ->
            viewModel.updateNotificationGlobal(notification, enabled)
        },
        onSwitchAllNotification = { enabled ->
            notificationsAll = enabled
            prefs.setNotificationsEnabled(enabled)
        },
        onRestartNotifications = {
            AppAlarmManager.startAllAlarms(context)
            alarmsEnabled = AppAlarmManager.isAlarmsRunning(context)
            scaffoldState.showSnackBar(context, R.string.settings_alarms_restarted)
        },
        onExportClick = {
            documentCreator.createFile()
        },
        onImportClick = {
            importOld = false
            documentPicker.openFile()
        },
        onImportOldClick = {
            importOld = true
            documentPicker.openFile()
        }
    )
}

@Composable
private fun SettingsScreen(
    hour: Int,
    minute: Int,
    enabled: Boolean,
    notificationsGlobal: List<NotificationGlobal>,
    alarmsEnabled: Boolean,
    onTimeChange: (Int, Int) -> Unit,
    onSwitchNotification: (NotificationGlobal, Boolean) -> Unit,
    onSwitchAllNotification: (Boolean) -> Unit,
    onRestartNotifications: () -> Unit,
    onExportClick: () -> Unit,
    onImportClick: () -> Unit,
    onImportOldClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsSectionHeader(
            text = stringResource(R.string.settings_notifications),
            modifier = Modifier.padding(top = 16.dp)
        )
        SimpleEventsTimePicker(
            hourInit = hour,
            minuteInit = minute,
            title = stringResource(R.string.settings_notifications_time),
            onTimeSelected = onTimeChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        SettingsAllNotifications(
            enabled = enabled,
            onSwitchAllNotification = onSwitchAllNotification,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        SettingsNotifications(
            enabled = enabled,
            notificationsGlobal = notificationsGlobal,
            onSwitchNotification = onSwitchNotification,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            text = stringResource(R.string.settings_alarms_is_enabled, alarmsEnabled),
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SimpleEventsButton(
            text = stringResource(R.string.settings_restart_alarms),
            onClick = onRestartNotifications,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        )
        SettingsSectionHeader(
            text = stringResource(R.string.settings_backup),
            modifier = Modifier.padding(top = 16.dp)
        )
        SettingsBackupButtons(
            onExportClick = onExportClick,
            onImportClick = onImportClick,
            onImportOldClick = onImportOldClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp, top = 8.dp)
        )
    }
}

@Composable
private fun SettingsSectionHeader(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = MaterialTheme.colors.primary,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.backgroundSecondary)
            .padding(16.dp)
    )
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
        Text(
            text = stringResource(R.string.settings_notifications_enable),
            color = MaterialTheme.colors.primary,
            fontSize = 16.sp,
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
    notificationsGlobal: List<NotificationGlobal>,
    onSwitchNotification: (NotificationGlobal, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        notificationsGlobal.forEach { notification ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(notification.nameRes),
                    color = MaterialTheme.colors.primary,
                    fontSize = 16.sp,
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
private fun SettingsBackupButtons(
    onExportClick: () -> Unit,
    onImportClick: () -> Unit,
    onImportOldClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SimpleEventsButton(
            text = stringResource(R.string.settings_backup_export),
            onClick = onExportClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        SimpleEventsButton(
            text = stringResource(R.string.settings_backup_import),
            onClick = onImportClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        SimpleEventsButton(
            text = stringResource(R.string.settings_backup_import_old),
            onClick = onImportOldClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    SimpleEventsTheme {
        SettingsScreen(
            hour = 7,
            minute = 9,
            enabled = true,
            notificationsGlobal = getDefaultNotificationsGlobal(),
            alarmsEnabled = true,
            onTimeChange = { _, _ -> },
            onSwitchNotification = { _, _ -> },
            onSwitchAllNotification = {},
            onRestartNotifications = {},
            onExportClick = {},
            onImportClick = {},
            onImportOldClick = {}
        )
    }
}