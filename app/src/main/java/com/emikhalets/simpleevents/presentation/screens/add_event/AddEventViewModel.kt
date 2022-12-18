package com.emikhalets.simpleevents.presentation.screens.add_event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import com.emikhalets.simpleevents.domain.usecase.AddEventUseCase
import com.emikhalets.simpleevents.utils.enums.EventType
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
        withoutYear: Boolean,
    ) {
        viewModelScope.launch {
            userCase.saveEvent(EventEntity(date, name, type, withoutYear))
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