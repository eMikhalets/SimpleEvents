package com.emikhalets.simpleevents.presentation.screens.edit_event

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.usecase.EditEventUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val useCase: EditEventUseCase,
) : BaseViewModel<EditEventState>() {

    override fun createInitialState(): EditEventState = EditEventState()

    fun resetError() = setState { it.copy(error = null) }
    fun resetUpdated() = setState { it.copy(updated = false) }

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

    fun updateEvent(event: EventEntity) {
        launchIO {
            setState { it.copy(loading = true) }
            useCase.updateEvent(event)
                .onSuccess {
                    setState { it.copy(loading = false, updated = true) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
