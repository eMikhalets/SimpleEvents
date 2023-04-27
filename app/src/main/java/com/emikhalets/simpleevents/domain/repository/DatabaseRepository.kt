package com.emikhalets.simpleevents.domain.repository

import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.GroupEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntity): Result<Long>
    suspend fun updateEvent(entity: EventEntity): Result<Int>
    suspend fun deleteEvent(entity: EventEntity): Result<Int>
    suspend fun getAllEvents(): Result<Flow<List<EventEntity>>>
    suspend fun getEventById(eventId: Long): Result<EventEntity>
    suspend fun getEventsByGroup(entity: GroupEntity): Result<Flow<List<EventEntity>>>

    /** Event Alarms Dao */

    suspend fun insertEventAlarm(entity: AlarmEntity): Result<Long>
    suspend fun insertEventAlarm(list: List<AlarmEntity>): Result<List<Long>>
    suspend fun updateEventAlarm(entity: AlarmEntity): Result<Int>
    suspend fun deleteEventAlarm(entity: AlarmEntity): Result<Int>
    suspend fun getAllEventsAlarm(): Result<Flow<List<AlarmEntity>>>

    /** Event Groups Dao */

    suspend fun insertGroup(entity: GroupEntity): Result<Long>
    suspend fun updateGroup(entity: GroupEntity): Result<Int>
    suspend fun deleteGroup(entity: GroupEntity): Result<Int>
    suspend fun getAllGroups(): Result<Flow<List<GroupEntity>>>
    suspend fun getGroupById(id: Long): Result<Flow<GroupEntity>>
}
