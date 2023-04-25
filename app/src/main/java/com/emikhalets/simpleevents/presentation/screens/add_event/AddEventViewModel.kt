package com.emikhalets.simpleevents.presentation.screens.add_event

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.usecase.events.AddEventUseCase
import com.emikhalets.simpleevents.presentation.screens.events_list.EventsListAction
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val addEventUseCase: AddEventUseCase,
) : BaseViewModel<AddEventState, EventsListAction>() {

    override fun createInitialState(): AddEventState = AddEventState()

    override fun handleEvent(action: EventsListAction) {
    }

    fun resetError() = setState { it.copy(error = null) }

    fun saveNewEvent(
        name: String,
        date: Long,
        type: EventType,
        withoutYear: Boolean,
    ) {
        launchIO {
            setState { it.copy(loading = true) }
            val entity = EventEntity(
                id = 0,
                date = date,
                name = name,
                eventType = type,
                note = "",
                withoutYear = withoutYear,
                days = 0,
                age = 0,
            )
            addEventUseCase(entity)
                .onSuccess { result ->
                    if (result is Long) {
                        setState { it.copy(loading = false, savedId = result) }
                    }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
