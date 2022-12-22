package com.emikhalets.simpleevents.presentation.screens.add_event

import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.usecase.AddEventUseCase
import com.emikhalets.simpleevents.utils.BaseViewModel
import com.emikhalets.simpleevents.utils.UiString
import com.emikhalets.simpleevents.utils.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val userCase: AddEventUseCase,
) : BaseViewModel<AddEventState>() {

    override fun createInitialState(): AddEventState = AddEventState()

    fun resetError() = setState { it.copy(error = null) }

    fun saveNewEvent(
        name: String,
        date: Long,
        type: EventType,
        withoutYear: Boolean,
    ) {
        launchIO {
            setState { it.copy(loading = true) }
            userCase.saveEvent(EventEntity(date, name, type, withoutYear))
                .onSuccess { result ->
                    setState { it.copy(loading = false, savedId = result) }
                }
                .onFailure { error ->
                    val uiError = UiString.Message(error.message)
                    setState { it.copy(loading = false, error = uiError) }
                }
        }
    }
}
