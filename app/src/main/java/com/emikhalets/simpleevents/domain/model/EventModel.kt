package com.emikhalets.simpleevents.domain.model

import com.emikhalets.simpleevents.utils.enums.EventType

data class EventModel(
    val id: Long,
    val date: Long,
    val name: String,
    val eventType: EventType,
    val note: String,
    val withoutYear: Boolean,
    val days: Int,
    val age: Int,
)
