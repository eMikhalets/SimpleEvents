package com.emikhalets.simpleevents.ui.screens.add_event

import com.emikhalets.simpleevents.domain.entity.EventEntity

data class AddEventState(
    val event: EventEntity? = null,
    val savedId: Long = 0,
    val error: String = "",
)
