package com.emikhalets.simpleevents.presentation.screens.event_item

import com.emikhalets.simpleevents.domain.model.EventModel
import com.emikhalets.simpleevents.domain.use_case.events.DeleteEventUseCase
import com.emikhalets.simpleevents.domain.use_case.events.GetEventsUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventItemViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
) : BaseViewModel<EventItemState, EventsListAction>() {

    override fun createInitialState(): EventItemState = EventItemState()

    override fun handleEvent(action: EventsListAction) {
    }

    fun resetError() = setState { it.copy(error = null) }

    fun loadEvent(id: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            getEventsUseCase(id)
                .onSuccess { result ->
                    val event = result.calculateEventData()
                    setState { it.copy(loading = false, event = event) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }

    fun deleteEvent(entity: EventModel?) {
        if (entity == null) {
            setState { it.copy(error = UiString.internal) }
            return
        }

        launchIO {
            deleteEventUseCase(entity)
                .onSuccess {
                    setState { it.copy(deleted = true) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(deleted = false, error = uiError) }
                }
        }
    }
}
