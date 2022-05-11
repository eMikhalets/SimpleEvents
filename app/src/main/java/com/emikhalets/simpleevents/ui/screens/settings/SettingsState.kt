package com.emikhalets.simpleevents.ui.screens.settings

import com.emikhalets.simpleevents.data.database.NotificationGlobal

data class SettingsState(
    val notificationsGlobal: List<NotificationGlobal> = emptyList(),
    val error: String = "",
)
