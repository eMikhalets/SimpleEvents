package com.emikhalets.simpleevents.ui.entity

import com.emikhalets.simpleevents.utils.enums.EventType

data class EventEntity(
    val date: Long,
    val name: String,
    val ageTurns: Int,
    val daysCount: Int,
    val eventType: EventType
)
