package com.emikhalets.simpleevents.ui.screens.event_item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.usecase.EventItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventItemViewModel @Inject constructor(
    private val useCase: EventItemUseCase,
) : ViewModel() {

    var state by mutableStateOf(EventItemState())
        private set

    fun loadEvent(id: Long) {
        viewModelScope.launch {
            useCase.loadEvent(id)
                .onSuccess {
                    state = state.copy(
                        event = mapToEventEntity(it)
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }

    private fun mapToEventEntity(entity: EventEntityDB): EventEntity {
        return EventEntity(
            id = entity.id,
            date = entity.date,
            name = entity.name,
            ageTurns = entity.ageTurns,
            daysCount = entity.daysCount,
            eventType = entity.eventType,
            note = entity.note,
        )
    }
}