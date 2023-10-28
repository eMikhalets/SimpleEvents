package com.emikhalets.simpleevents.presentation.screens.edit_event

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class EditEventState(
    val event: EventModel? = null,
    val loading: Boolean = false,
    val updated: Boolean = false,
    val error: UiString? = null,
) : AppState
