package com.emikhalets.simpleevents.utils

import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.EventEntityOld
import com.emikhalets.simpleevents.utils.enums.EventType

object Mappers {

    fun mapFromEventsListToEventsDbList(list: List<EventEntity>): List<EventEntityDB> {
        return list.map { mapFromEventToEventDb(it) }
    }

    fun mapFromEventsDbListToEventsList(list: List<EventEntityDB>): List<EventEntity> {
        return list.map { mapFromEventDbToEvent(it) }
    }

    fun mapFromOldEventsListToEventsDbList(list: List<EventEntityOld>): List<EventEntityDB> {
        return list.map { mapFromOldEventToEventDb(it) }
    }

    fun mapFromEventToEventDb(event: EventEntity): EventEntityDB {
        return EventEntityDB(
            id = event.id,
            date = event.date,
            name = event.name,
            ageTurns = event.ageTurns,
            daysCount = event.daysCount,
            eventType = event.eventType,
            note = event.note,
        )
    }

    fun mapFromEventDbToEvent(event: EventEntityDB): EventEntity {
        return EventEntity(
            id = event.id,
            date = event.date,
            name = event.name,
            ageTurns = event.ageTurns,
            daysCount = event.daysCount,
            eventType = event.eventType,
            note = event.note,
        )
    }

    fun mapFromOldEventToEventDb(eventOld: EventEntityOld): EventEntityDB {
        return EventEntityDB(
            id = eventOld.id,
            date = eventOld.date,
            name = eventOld.fullName(),
            ageTurns = eventOld.age,
            daysCount = eventOld.daysLeft,
            eventType = EventType.BIRTHDAY,
            note = eventOld.notes
        )
    }
}