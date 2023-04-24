package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.AlarmEntity
import com.emikhalets.simpleevents.domain.entity.EventEntity
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    suspend fun loadEventsAlarm(): Result<Flow<List<AlarmEntity>>>
    suspend fun getAllEvents(): Result<List<EventEntity>>
    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean>
    suspend fun addNotification(notification: AlarmEntity): Result<Long>
    suspend fun updateNotification(notification: AlarmEntity): Result<Int>
    suspend fun deleteNotification(notification: AlarmEntity): Result<Int>
}
