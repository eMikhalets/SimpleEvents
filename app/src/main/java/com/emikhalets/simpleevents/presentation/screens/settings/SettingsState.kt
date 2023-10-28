package com.emikhalets.simpleevents.presentation.screens.settings

import com.emikhalets.simpleevents.domain.model.AlarmModel
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class SettingsState(
    val eventAlarms: List<AlarmModel> = emptyList(),
    val imported: Boolean = false,
    val exported: Boolean = false,
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState
