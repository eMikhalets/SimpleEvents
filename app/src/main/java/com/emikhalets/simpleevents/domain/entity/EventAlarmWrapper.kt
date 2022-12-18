package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

data class EventAlarmWrapper(
    val alarm: EventAlarm,
    val events: List<EventEntity>,
)
