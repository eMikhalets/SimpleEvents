package com.emikhalets.simpleevents.presentation.screens.home

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.usecase.HomeUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import com.emikhalets.simpleevents.utils.extensions.formatDateMonth
import com.emikhalets.simpleevents.utils.extensions.localDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : BaseViewModel<HomeState>() {

    private var searchJob: Job? = null

    override fun createInitialState(): HomeState = HomeState()

    fun resetError() = setState { it.copy(error = null) }

    fun loadAllEvents() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.loadAllEvents()
                .onSuccess { result ->
                    val sortedEvents = computeAndSortEvents(result)
                    val homeEvents = mapEventsHeaders(sortedEvents)
                    setState {
                        it.copy(loading = false,
                            homeEvents = homeEvents,
                            searchedEvents = homeEvents)
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun searchEvents(query: String) {
        if (searchJob != null && searchJob?.isActive == true) searchJob?.cancel()
        searchJob = launchIO {
            delay(500)
            val homeEvents = currentState.homeEvents
            if (query.isEmpty()) {
                setState { it.copy(searchedEvents = homeEvents) }
            } else {
                val eventsList = homeEvents
                    .mapNotNull { if (it is HomeEventEntity) it.event else null }
                    .filter { it.name.lowercase().contains(query) }
                val mappedEvents = mapEventsHeaders(eventsList)
                setState { it.copy(searchedEvents = mappedEvents) }
            }
        }
    }

    private fun computeAndSortEvents(events: List<EventEntity>): List<EventEntity> {
        return events
            .map { event -> event.calculateEventData() }
            .sortedBy { event -> event.days }
    }

    private fun mapEventsHeaders(events: List<EventEntity>): List<HomeEvent> {
        var currentMonth = -1
        val homeEvents = mutableListOf<HomeEvent>()

        events.forEach { event ->
            val eventMonth = event.date.localDate.monthValue
            if (eventMonth != currentMonth) {
                currentMonth = eventMonth
                val monthName = event.date.formatDateMonth()
                homeEvents.add(HomeMonthHeader(monthName))
            }
            homeEvents.add(HomeEventEntity(event))
        }

        return homeEvents
    }
}
