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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.data.database.NotificationGlobal
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsButton
import com.emikhalets.simpleevents.ui.screens.common.SimpleEventsTimePicker
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.utils.AppAlarmManager
import com.emikhalets.simpleevents.utils.Prefs
import com.emikhalets.simpleevents.utils.extensions.getDefaultNotificationsGlobal
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

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

    LaunchedEffect("") {
        viewModel.loadAllNotificationsGlobal()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    SettingsScreen(
        hour = hourInit,
        minute = minuteInit,
        enabled = notificationsAll,
        notificationsGlobal = state.notificationsGlobal,
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
            scaffoldState.showSnackBar(context, R.string.settings_alarms_restarted)
        }
    )
}

@Composable
private fun SettingsScreen(
    hour: Int,
    minute: Int,
    enabled: Boolean,
    notificationsGlobal: List<NotificationGlobal>,
    onTimeChange: (Int, Int) -> Unit,
    onSwitchNotification: (NotificationGlobal, Boolean) -> Unit,
    onSwitchAllNotification: (Boolean) -> Unit,
    onRestartNotifications: () -> Unit,
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

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    SimpleEventsTheme {
        SettingsScreen(
            hour = 7,
            minute = 9,
            enabled = true,
            notificationsGlobal = getDefaultNotificationsGlobal(),
            onTimeChange = { _, _ -> },
            onSwitchNotification = { _, _ -> },
            onSwitchAllNotification = {},
            onRestartNotifications = {}
        )
    }
}