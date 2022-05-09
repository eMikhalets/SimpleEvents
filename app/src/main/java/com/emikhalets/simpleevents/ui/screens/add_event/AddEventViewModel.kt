package com.emikhalets.simpleevents.ui.screens.add_event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.domain.usecase.AddEventUseCase
import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.calculateEventData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val userCase: AddEventUseCase,
) : ViewModel() {

    var state by mutableStateOf(AddEventState())
        private set

    fun saveNewEvent(
        name: String,
        date: Long,
        type: EventType,
    ) {
        viewModelScope.launch {
            val entity = EventEntityDB(
                date = date,
                name = name,
                eventType = type
            )
            userCase.saveEvent(entity.calculateEventData())
                .onSuccess {
                    state = state.copy(
                        savedId = it
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