package com.emikhalets.simpleevents.presentation.screens.home

import com.emikhalets.simpleevents.utils.AppState
import com.emikhalets.simpleevents.utils.UiString

data class HomeState(
    val homeEvents: List<HomeEvent> = emptyList(),
    val searchedEvents: List<HomeEvent> = emptyList(),
    val loading: Boolean = false,
    val error: UiString? = null,
) : AppState
