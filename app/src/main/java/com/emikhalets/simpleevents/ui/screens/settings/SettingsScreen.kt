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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.emikhalets.simpleevents.R
import com.emikhalets.simpleevents.data.database.NotificationGlobal
import com.emikhalets.simpleevents.ui.theme.SimpleEventsTheme
import com.emikhalets.simpleevents.ui.theme.backgroundSecondary
import com.emikhalets.simpleevents.utils.extensions.getDefaultNotificationsGlobal
import com.emikhalets.simpleevents.utils.extensions.showSnackBar

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
) {
    val state = viewModel.state

    LaunchedEffect("") {
        viewModel.loadAllNotificationsGlobal()
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) scaffoldState.showSnackBar(state.error)
    }

    SettingsScreen(
        notificationsGlobal = state.notificationsGlobal,
        onSwitchNotification = { viewModel.updateNotificationGlobal(it) }
    )
}

@Composable
private fun SettingsScreen(
    notificationsGlobal: List<NotificationGlobal>,
    onSwitchNotification: (NotificationGlobal) -> Unit,
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
        SettingsNotifications(
            notificationsGlobal = notificationsGlobal,
            onSwitchNotification = onSwitchNotification,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
private fun SettingsNotifications(
    notificationsGlobal: List<NotificationGlobal>,
    onSwitchNotification: (NotificationGlobal) -> Unit,
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
                    onCheckedChange = { onSwitchNotification(notification) }
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
            notificationsGlobal = getDefaultNotificationsGlobal(),
            onSwitchNotification = {}
        )
    }
}