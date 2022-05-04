package com.emikhalets.simpleevents.ui.screens.event_item

import com.emikhalets.simpleevents.domain.entity.EventEntity

data class EventItemState(
    val event: EventEntity? = null,
    val error: String = "",
)
