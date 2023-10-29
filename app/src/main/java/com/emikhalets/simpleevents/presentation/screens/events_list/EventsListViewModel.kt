package com.emikhalets.simpleevents.presentation.screens.events_list

import com.emikhalets.simpleevents.core.common.mvi.MviViewModel
import com.emikhalets.simpleevents.core.common.mvi.launch
import com.emikhalets.simpleevents.domain.StringValue
import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.domain.use_case.events.GetEventsUseCase
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListContract.Action
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListContract.Effect
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListContract.State
import com.emikhalets.simpleevents.utils.extensions.getMonthEdges
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch

@HiltViewModel
class EventsListViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
) : MviViewModel<Action, Effect, State>() {

    private var searchJob: Job? = null

    init {
        launch {
            getEventsUseCase()
                .catch { setFailureState(it) }
                .collect { setEventsList(it) }
        }
    }

    override fun setInitialState() = State()

    override fun handleAction(action: Action) {
        when (action) {
            is Action.SearchEvents -> searchEvents(action.query)
            Action.DropError -> dropError()
        }
    }

    private fun dropError() {
        setState { it.copy(error = null) }
    }

    private fun searchEvents(query: String) {
        if (searchJob?.isActive == true) searchJob?.cancel()
        searchJob = launch {
            delay(500)
            if (query.isEmpty()) {
                val mappedList = mapEventsList(currentState.eventsList)
                setState { it.copy(eventsMap = mappedList) }
            } else {
                val oldList = currentState.eventsList
                val newList = oldList.filter { it.name.lowercase().contains(query) }
                val mappedList = mapEventsList(newList)
                setState { it.copy(eventsMap = mappedList) }
            }
        }
    }

    private fun setEventsList(list: List<EventModel>) {
        val mappedList = mapEventsList(list)
        setState { it.copy(loading = false, eventsMap = mappedList) }
    }

    private fun mapEventsList(events: List<EventModel>): Map<Long, List<EventModel>> {
        return events
            .sortedBy { event -> event.days }
            .also { list -> setState { it.copy(eventsList = list) } }
            .groupBy { it.date.getMonthEdges().first }
    }

    private fun setFailureState(throwable: Throwable) {
        setState { it.copy(error = StringValue.create(throwable)) }
    }
}
