package com.emikhalets.simpleevents.domain.model

data class AlarmWithEventsModel(
    val alarm: AlarmModel,
    val events: List<EventModel>,
)
