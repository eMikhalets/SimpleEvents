package com.emikhalets.simpleevents.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.simpleevents.domain.usecase.HomeUseCase
import com.emikhalets.simpleevents.utils.Mappers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: HomeUseCase,
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    fun loadAllEvents() {
        viewModelScope.launch {
            useCase.loadAllEvents()
                .onSuccess {
                    state = state.copy(
                        events = Mappers.mapFromEventsDbListToEventsList(it)
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