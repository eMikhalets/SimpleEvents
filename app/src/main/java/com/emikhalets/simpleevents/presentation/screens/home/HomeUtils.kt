package com.emikhalets.simpleevents.presentation.screens.home

import com.emikhalets.simpleevents.domain.entity.EventEntity

interface HomeEvent

data class HomeEventEntity(
    val event: EventEntity,
) : HomeEvent

data class HomeMonthHeader(
    val monthName: String,
) : HomeEvent
