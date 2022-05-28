package com.emikhalets.simpleevents.ui.screens.settings

import com.emikhalets.simpleevents.domain.entity.NotificationGlobal

data class SettingsState(
    val notificationsGlobal: List<NotificationGlobal> = emptyList(),
    val imported: Boolean = false,
    val error: String = "",
)
