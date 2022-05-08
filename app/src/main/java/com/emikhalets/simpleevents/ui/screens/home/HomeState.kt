package com.emikhalets.simpleevents.ui.screens.home

import com.emikhalets.simpleevents.domain.entity.EventEntity

data class HomeState(
    val events: List<EventEntity> = emptyList(),
    val error: String = "",
)
