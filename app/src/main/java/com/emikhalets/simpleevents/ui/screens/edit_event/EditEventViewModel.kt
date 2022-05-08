package com.emikhalets.simpleevents.ui.screens.edit_event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.utils.enums.EventType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEventViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(EditEventState())
        private set

    fun loadEvent(id: Long) {
        viewModelScope.launch {
            // TODO: load event by id
        }
    }

    fun updateEvent(
        name: String,
        date: Long,
        type: EventType,
        note: String,
    ) {
        viewModelScope.launch {
            // TODO: build db entity, insert and return id in state
        }
    }
}