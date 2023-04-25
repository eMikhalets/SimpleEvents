package com.emikhalets.simpleevents.domain.entity

import com.emikhalets.simpleevents.utils.enums.EventType
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.milliseconds
import com.emikhalets.simpleevents.utils.extensions.turns
import java.time.LocalDate

object PreviewEntity {

    fun getEventEntity(id: Long): EventEntity {
        val date = LocalDate.now().milliseconds
        return EventEntity(
            id = id,
            name = "Name id",
            date = date,
            eventType = EventType.BIRTHDAY,
            note = "",
            withoutYear = false,
            days = date.daysLeft,
            age = date.turns
        )
    }

    fun getEventListScreenEvents(): Map<Long, List<EventEntity>> {
        return mapOf(
            LocalDate.now().milliseconds to listOf(
                getEventEntity(1),
                getEventEntity(2),
                getEventEntity(3),
                getEventEntity(4),
            ),
            LocalDate.now().minusMonths(1).milliseconds to listOf(
                getEventEntity(1),
                getEventEntity(2),
            ),
            LocalDate.now().minusMonths(2).milliseconds to listOf(
                getEventEntity(1),
                getEventEntity(2),
                getEventEntity(3),
                getEventEntity(4),
                getEventEntity(5),
            ),
        )
    }
}
