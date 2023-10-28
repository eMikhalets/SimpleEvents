package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class EventsListState(
    val eventsMap: Map<Long, List<EventModel>> = emptyMap(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState
