package com.emikhalets.simpleevents.presentation.screens.events_calendar

import com.emikhalets.simpleevents.utils.AppAction

sealed class EventsCalendarAction : AppAction {
    object AcceptError : EventsCalendarAction()
    object GetEvents : EventsCalendarAction()
}
