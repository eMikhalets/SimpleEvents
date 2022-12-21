package com.emikhalets.simpleevents.presentation.screens.event_item

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.usecase.EventItemUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventItemViewModel @Inject constructor(
    private val useCase: EventItemUseCase,
) : BaseViewModel<EventItemState>() {

    override fun createInitialState(): EventItemState = EventItemState()

    fun resetError() = setState { it.copy(error = null) }

    fun loadEvent(id: Long) {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.loadEvent(id)
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

    fun deleteEvent(entity: EventEntity?) {
        if (entity == null) {
            setState { it.copy(error = UiString.internal) }
            return
        }

        launchIO {
            useCase.deleteEvent(entity)
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
