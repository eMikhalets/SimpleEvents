package com.emikhalets.simpleevents.data.repository

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.EventEntity
import com.emikhalets.simpleevents.domain.entity.NotificationGlobal

interface DatabaseRepository {

    /** Events Dao */

    suspend fun insertEvent(entity: EventEntity): Result<Long>

    suspend fun updateEvent(entity: EventEntity): Result<Int>

    suspend fun deleteEvent(entity: EventEntity): Result<Int>

    suspend fun getAllEvents(): Result<List<EventEntity>>

    suspend fun getEntityById(eventId: Long): Result<EventEntity>

    suspend fun importEvents(uri: Uri, isOld: Boolean): Result<List<Long>>

    /** Notifications Global Dao */

    suspend fun insertNotifGlobal(entity: NotificationGlobal): Result<Long>

    suspend fun insertNotifGlobal(list: List<NotificationGlobal>): Result<List<Long>>

    suspend fun updateNotifGlobal(entity: NotificationGlobal): Result<Int>

    suspend fun getAllNotifGlobal(): Result<List<NotificationGlobal>>
}