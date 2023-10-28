package com.emikhalets.simpleevents.presentation.screens.events_calendar

import com.emikhalets.simpleevents.domain.use_case.events.GetEventsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class EventsCalendarViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : BaseViewModel<EventsCalendarState, EventsCalendarAction>() {

    override fun createInitialState() = EventsCalendarState()

    override fun handleEvent(action: EventsCalendarAction) {
        when (action) {
            EventsCalendarAction.GetEvents -> getEvents()
            EventsCalendarAction.AcceptError -> resetError()
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getEvents() {
        launchIO {
            setState { it.copy(loading = true) }
            getEventsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, eventsList = list) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
