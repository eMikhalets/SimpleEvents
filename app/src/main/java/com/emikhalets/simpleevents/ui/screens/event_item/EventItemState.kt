package com.emikhalets.simpleevents.ui.screens.event_item

import com.emikhalets.simpleevents.domain.entity.database.EventEntity

data class EventItemState(
    val event: EventEntity? = null,
    val deleted: Boolean = false,
    val error: String = "",
)
