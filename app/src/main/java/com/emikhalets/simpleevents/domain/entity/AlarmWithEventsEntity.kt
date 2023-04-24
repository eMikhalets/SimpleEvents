package com.emikhalets.simpleevents.domain.entity

data class AlarmWithEventsEntity(
    val alarm: AlarmEntity,
    val events: List<EventEntity>,
)
