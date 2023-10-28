package com.emikhalets.simpleevents.presentation.screens.events_calendar

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class EventsCalendarState(
    val eventsList: List<EventModel> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState
