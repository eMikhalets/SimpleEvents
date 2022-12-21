package com.emikhalets.simpleevents.presentation.screens.home

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

data class HomeState(
    val events: List<EventEntity> = emptyList(),
    val error: String = "",
)