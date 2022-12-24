package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    suspend fun loadEventsAlarm(): Result<Flow<List<EventAlarm>>>
    suspend fun getAllEvents(): Result<List<EventEntity>>
    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean>
    suspend fun updateNotification(notification: EventAlarm): Result<Int>
    suspend fun deleteNotification(notification: EventAlarm): Result<Int>
}
