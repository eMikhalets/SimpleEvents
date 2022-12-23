package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity
import kotlinx.coroutines.flow.Flow

interface SettingsUseCase {

    suspend fun loadNotificationsGlobal(): Result<Flow<List<EventAlarm>>>
    suspend fun updateNotificationsGlobal(entity: EventAlarm): Result<Int>
    suspend fun getAllEvents(): Result<List<EventEntity>>
    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(uri: Uri, events: List<EventEntity>): Result<Boolean>
}
