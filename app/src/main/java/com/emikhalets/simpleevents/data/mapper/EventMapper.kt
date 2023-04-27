package com.emikhalets.simpleevents.data.mapper

import com.emikhalets.simpleevents.data.database.entity.EventDb
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.utils.extensions.daysLeft
import com.emikhalets.simpleevents.utils.extensions.turns

object EventMapper {

    fun mapDbToEntity(entity: EventDb): EventEntity = EventEntity(
        id = entity.id,
        date = entity.date,
        name = entity.name,
        eventType = entity.eventType,
        note = entity.note,
        withoutYear = entity.withoutYear,
        groupId = entity.groupId,
        days = entity.date.daysLeft,
        age = entity.date.turns
    )

    fun mapEntityToDb(entity: EventEntity): EventDb = EventDb(
        id = entity.id,
        date = entity.date,
        name = entity.name,
        eventType = entity.eventType,
        note = entity.note,
        withoutYear = entity.withoutYear,
        groupId = entity.groupId
    )

    fun mapDbListToList(list: List<EventDb>): List<EventEntity> = list.map {
        mapDbToEntity(it)
    }

    fun mapListToDbList(list: List<EventEntity>): List<EventDb> = list.map {
        mapEntityToDb(it)
    }
}
