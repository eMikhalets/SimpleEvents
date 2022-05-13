package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.data.database.EventEntityDB
import com.emikhalets.simpleevents.data.database.NotificationGlobal

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntityDB): Result<Long>

    suspend fun updateEvent(entity: EventEntityDB): Result<Int>

    suspend fun deleteEvent(entity: EventEntityDB): Result<Int>

    suspend fun getAllEvents(): Result<List<EventEntityDB>>

    suspend fun getEntityById(eventId: Long): Result<EventEntityDB>

    suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>>

    /** Notifications Global Dao */

    suspend fun insertNotifGlobal(entity: NotificationGlobal): Result<Long>

    suspend fun insertNotifGlobal(list: List<NotificationGlobal>): Result<List<Long>>

    suspend fun updateNotifGlobal(entity: NotificationGlobal): Result<Int>

    suspend fun getAllNotifGlobal(): Result<List<NotificationGlobal>>
}