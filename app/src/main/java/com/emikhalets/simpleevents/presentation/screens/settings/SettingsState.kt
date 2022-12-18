package com.emikhalets.simpleevents.presentation.screens.settings

data class SettingsState(
    val notificationsGlobal: List<NotificationGlobal> = emptyList(),
    val imported: Boolean = false,
    val error: String = "",
)
