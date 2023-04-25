package com.emikhalets.simpleevents.domain.repository

import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntity): Result<Long>
    suspend fun updateEvent(entity: EventEntity): Result<Int>
    suspend fun deleteEvent(entity: EventEntity): Result<Int>
    suspend fun getAllEvents(): Result<Flow<List<EventEntity>>>
    suspend fun getEntityById(eventId: Long): Result<EventEntity>

    /** Event Alarms Dao */

    suspend fun insertEventAlarm(entity: AlarmEntity): Result<Long>
    suspend fun insertEventAlarm(list: List<AlarmEntity>): Result<List<Long>>
    suspend fun updateEventAlarm(entity: AlarmEntity): Result<Int>
    suspend fun deleteEventAlarm(entity: AlarmEntity): Result<Int>
    suspend fun getAllEventsAlarm(): Result<Flow<List<AlarmEntity>>>
}
