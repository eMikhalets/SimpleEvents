package com.emikhalets.simpleevents.utils

import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.EventEntityOld
import com.emikhalets.simpleevents.utils.enums.EventType

object Mappers {

    fun mapFromOldEventsListToEventsDbList(list: List<EventEntityOld>): List<EventEntity> {
        return list.map { mapFromOldEventToEvent(it) }
    }

    fun mapFromOldEventToEvent(eventOld: EventEntityOld): EventEntity {
        return EventEntity(
            id = eventOld.id,
            date = eventOld.date,
            name = eventOld.fullName(),
            age = eventOld.age,
            days = eventOld.daysLeft,
            eventType = EventType.BIRTHDAY,
            note = eventOld.notes
        )
    }
}