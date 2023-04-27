package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.usecase.events.GetEventsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.getMonthEdges
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class EventsListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : BaseViewModel<EventsListState, EventsListAction>() {

    private var searchJob: Job? = null
    private var eventsList = listOf<EventEntity>()

    override fun createInitialState() = EventsListState()

    override fun handleEvent(action: EventsListAction) {
        when (action) {
            EventsListAction.GetEvents -> getEvents()
            EventsListAction.AcceptError -> resetError()
            is EventsListAction.SearchEvents -> searchEvents(action.query)
        }
    }

    private fun resetError() = setState { it.copy(error = null) }

    private fun getEvents() {
        launchIO {
            setState { it.copy(loading = true) }
            getEventsUseCase()
                .onSuccess { result ->
                    result.collectLatest { list ->
                        setState { it.copy(loading = false, eventsMap = mapEventsList(list)) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    private fun searchEvents(query: String) {
        if (searchJob != null && searchJob?.isActive == true) searchJob?.cancel()
        searchJob = launchIO {
            delay(500)
            if (query.isEmpty()) {
                setState { it.copy(eventsMap = mapEventsList(eventsList)) }
            } else {
                val newList = eventsList.filter { it.name.lowercase().contains(query) }
                setState { it.copy(eventsMap = mapEventsList(newList)) }
            }
        }
    }

    private fun mapEventsList(events: List<EventEntity>): Map<Long, List<EventEntity>> {
        return events
            .sortedBy { event -> event.days }
            .also { eventsList = it }
            .groupBy { it.date.getMonthEdges().first }
    }
}
