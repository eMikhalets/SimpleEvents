package com.emikhalets.simpleevents.ui.screens.event_item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                        event = it
                    )
                }
                .onFailure {
                    state = state.copy(
                        error = it.localizedMessage ?: ""
                    )
                }
        }
    }

    fun deleteEvent(entity: EventEntity?) {
        entity ?: return

        viewModelScope.launch {
            useCase.deleteEvent(entity)
                .onSuccess {
                    state = state.copy(
                        deleted = true
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