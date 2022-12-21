package com.emikhalets.simpleevents.presentation.screens.home

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.usecase.HomeUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import com.emikhalets.simpleevents.utils.extensions.formatDateMonth
import com.emikhalets.simpleevents.utils.extensions.localDate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : BaseViewModel<HomeState>() {

    override fun createInitialState(): HomeState = HomeState()

    fun resetError() = setState { it.copy(error = null) }

    fun loadAllEvents() {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.loadAllEvents()
                .onSuccess { result ->
                    val homeEvents = mapEvents(result)
                    setState { it.copy(loading = false, homeEvents = homeEvents) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    private fun mapEvents(events: List<EventEntity>): List<HomeEvent> {
        val sortedEvents = events
            .map { event -> event.calculateEventData() }
            .sortedBy { event -> event.days }

        var currentMonth = -1
        val homeEvents = mutableListOf<HomeEvent>()

        sortedEvents.forEach { event ->
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
