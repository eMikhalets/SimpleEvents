package com.emikhalets.simpleevents.data.repository

import com.emikhalets.simpleevents.data.database.EventEntityDB

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntityDB): Result<Long>

    suspend fun updateEvent(entity: EventEntityDB): Result<Int>

    suspend fun deleteEvent(entity: EventEntityDB): Result<Int>

    suspend fun getAllEvents(): Result<List<EventEntityDB>>

    suspend fun getEntityById(eventId: Long): Result<EventEntityDB>
}