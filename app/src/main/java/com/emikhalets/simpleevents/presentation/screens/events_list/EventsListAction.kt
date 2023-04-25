package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.simpleevents.utils.AppAction

sealed class EventsListAction : AppAction {
    object AcceptError : EventsListAction()
    object GetEvents : EventsListAction()
    class SearchEvents(val query: String) : EventsListAction()
}
