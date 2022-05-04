package com.emikhalets.simpleevents.ui.screens.add_event

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
class AddEventViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(AddEventState())
        private set

    fun saveNewEvent(
        name: String,
        date: Long,
        type: EventType,
    ) {
        viewModelScope.launch {
            // TODO: build db entity, insert and return id in state
        }
    }
}