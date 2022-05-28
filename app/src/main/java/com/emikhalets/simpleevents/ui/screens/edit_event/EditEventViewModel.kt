package com.emikhalets.simpleevents.ui.screens.edit_event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.domain.usecase.EditEventUseCase
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
    private val useCase: EditEventUseCase,
) : ViewModel() {

    var state by mutableStateOf(EditEventState())
        private set

    fun loadEvent(id: Long) {
        viewModelScope.launch {
            useCase.loadEvent(id)
                .onSuccess {
                    state = state.copy(
                        event = it.calculateEventData()
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }

    fun updateEvent(
        name: String,
        date: Long,
        type: EventType,
        note: String,
    ) {
        state.event?.let { event ->
            viewModelScope.launch {
                val entity = event.copy(
                    name = name,
                    date = date,
                    eventType = type,
                    note = note
                )
                useCase.updateEvent(entity)
                    .onSuccess {
                        state = state.copy(
                            updated = it
                        )
                    }
                    .onFailure {
                        state = state.copy(
                            error = it.localizedMessage ?: ""
                        )
                    }
            }
        }
    }
}