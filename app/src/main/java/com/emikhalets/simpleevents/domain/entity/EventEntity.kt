package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.utils.enums.EventType

data class EventEntity(
    val id: Long,
    val date: Long,
    val name: String,
    val ageTurns: Int,
    val daysCount: Int,
    val eventType: EventType,
    val note: String,
)
