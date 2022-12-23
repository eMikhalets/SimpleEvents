package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntity): Result<Long>
    suspend fun updateEvent(entity: EventEntity): Result<Int>
    suspend fun deleteEvent(entity: EventEntity): Result<Int>
    suspend fun getAllEvents(): Result<List<EventEntity>>
    suspend fun getEntityById(eventId: Long): Result<EventEntity>

    /** Event Alarms Dao */

    suspend fun insertNotifGlobal(entity: EventAlarm): Result<Long>
    suspend fun insertNotifGlobal(list: List<EventAlarm>): Result<List<Long>>
    suspend fun updateNotifGlobal(entity: EventAlarm): Result<Int>
    suspend fun getAllNotifGlobal(): Result<Flow<List<EventAlarm>>>
}
