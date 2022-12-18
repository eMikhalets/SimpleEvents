package com.emikhalets.simpleevents.domain.usecase

import android.net.Uri
import com.emikhalets.simpleevents.domain.entity.database.EventAlarm
import com.emikhalets.simpleevents.domain.entity.database.EventEntity

interface SettingsUseCase {

    suspend fun loadNotificationsGlobal(): Result<List<EventAlarm>>
    suspend fun updateNotificationsGlobal(entity: EventAlarm): Result<Int>
    suspend fun importEvents(uri: Uri): Result<List<EventEntity>>
    suspend fun exportEvents(events: List<EventEntity>): Result<Boolean>
}
