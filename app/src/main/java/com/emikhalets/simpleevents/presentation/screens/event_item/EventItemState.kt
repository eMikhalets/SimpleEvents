package com.emikhalets.simpleevents.presentation.screens.event_item

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class EventItemState(
    val event: EventModel? = null,
    val loading: Boolean = false,
    val deleted: Boolean = false,
    val error: UiString? = null,
) : AppState
