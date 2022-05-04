package com.emikhalets.simpleevents.ui.screens.event_item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventItemViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(EventItemState())
        private set

    fun loadEvent(id: Long) {
        viewModelScope.launch {
            // TODO: load event by id
        }
    }
}